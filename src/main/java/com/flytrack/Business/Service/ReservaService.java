package com.flytrack.Business.Service;

import com.flytrack.Business.Dto.PasajeroDTO;
import com.flytrack.Business.Dto.ReservaRequest;
import com.flytrack.Persistence.Mapper.PasajeroMapper;
import com.flytrack.Persistence.Model.Pasajero;
import com.flytrack.Persistence.Model.Reserva;
import com.flytrack.Persistence.Model.Vuelo;
import com.flytrack.Persistence.Repository.PasajeroRepository;
import com.flytrack.Persistence.Repository.ReservaRepository;
import com.flytrack.Persistence.Repository.VueloRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Service
@RequiredArgsConstructor
public class ReservaService {

    private final ReservaRepository reservaRepositorio;
    private final PasajeroRepository pasajeroRepositorio;
    private final VueloRepository vueloRepositorio;
    private final PasajeroMapper pasajeroMapper;

    @Transactional
    public List<Reserva> crearReserva(ReservaRequest request) {

        Vuelo vuelo = vueloRepositorio.findById(request.getVueloId())
                .orElseThrow(() -> new RuntimeException("Vuelo no encontrado"));

        int capacidad = vuelo.getCapacidad();

        List<Reserva> reservas = new ArrayList<>();

        String codigoReserva = "RES-" + System.currentTimeMillis();

        List<Integer> asientosDisponibles = new ArrayList<>();

        for (int i = 1; i <= capacidad; i++) {
            asientosDisponibles.add(i);
        }

        List<Reserva> reservasExistentes = reservaRepositorio.findByVueloId(vuelo.getId());

        // 🔥 SET de documentos ya registrados en este vuelo
        Set<String> documentosEnVuelo = new HashSet<>();

        for (Reserva r : reservasExistentes) {

            // 🔹 quitar asientos ocupados
            if (r.getNumeroAsiento() != null) {
                try {
                    int ocupado = Integer.parseInt(r.getNumeroAsiento());
                    asientosDisponibles.remove(Integer.valueOf(ocupado));
                } catch (NumberFormatException ignored) {}
            }

            // 🔹 guardar documentos ya usados en este vuelo
            if (r.getPasajero() != null && r.getPasajero().getNumeroDocumento() != null) {
                documentosEnVuelo.add(r.getPasajero().getNumeroDocumento());
            }
        }

        if (asientosDisponibles.size() < request.getPasajeros().size()) {
            throw new RuntimeException("No hay suficientes asientos disponibles");
        }

        Collections.shuffle(asientosDisponibles);

        int index = 0;

        for (PasajeroDTO p : request.getPasajeros()) {

            // 🔥 VALIDACIÓN CORRECTA
            if (documentosEnVuelo.contains(p.getDocumento())) {
                throw new RuntimeException(
                        "El documento " + p.getDocumento() + " ya tiene una reserva en este vuelo"
                );
            }

            Pasajero pasajero = pasajeroMapper.toEntity(p);
            pasajeroRepositorio.save(pasajero);

            String asientoAsignado = String.valueOf(asientosDisponibles.get(index++));

            Reserva reserva = Reserva.builder()
                    .codigoReserva(codigoReserva)
                    .numeroAsiento(asientoAsignado)
                    .estadoTiquete("CONFIRMADO")
                    .pasajero(pasajero)
                    .vuelo(vuelo)
                    .build();

            reservas.add(reservaRepositorio.save(reserva));

            documentosEnVuelo.add(p.getDocumento());
        }

        return reservas;
    }

    public List<Reserva> obtenerReservasPorPasajero(Long pasajeroId) {
        return reservaRepositorio.findByPasajeroId(pasajeroId);
    }

    public Reserva obtenerReservaPorId(Long reservaId) {
        return reservaRepositorio.findById(reservaId)
                .orElseThrow(() -> new RuntimeException("Reserva no encontrada"));
    }
}