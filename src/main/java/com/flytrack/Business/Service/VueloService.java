package com.flytrack.Business.Service;

import com.flytrack.Persistence.Model.Notificacion;
import com.flytrack.Persistence.Model.Reserva;
import com.flytrack.Persistence.Model.Vuelo;
import com.flytrack.Persistence.Repository.NotificacionRepository;
import com.flytrack.Persistence.Repository.ReservaRepository;
import com.flytrack.Persistence.Repository.VueloRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
public class VueloService {

    private final VueloRepository vueloRepositorio;
    private final ReservaRepository reservaRepositorio;
    private final NotificacionRepository notificacionRepositorio;

    @Autowired
    private EmailService emailService;

    public Vuelo guardarVuelo(Vuelo vuelo) {
        return vueloRepositorio.save(vuelo);
    }

    public void eliminarVuelo(Long id) {
        Vuelo vuelo = vueloRepositorio.findById(id)
                .orElseThrow(() -> new RuntimeException("Vuelo no encontrado"));
        vueloRepositorio.delete(vuelo);
    }

    public List<Vuelo> obtenerTodos() {
        return vueloRepositorio.findAll();
    }

    public Vuelo obtenerPorId(Long id) {
        return vueloRepositorio.findById(id)
                .orElseThrow(() -> new RuntimeException("Vuelo no encontrado"));
    }

    public List<Vuelo> filtrarVuelos(String origen, String destino, LocalDate fecha, int pasajeros) {

        List<Vuelo> vuelos = vueloRepositorio.findByOrigenAndDestinoAndFecha(origen, destino, fecha);

        return vuelos.stream()
                .filter(vuelo -> {
                    int ocupados = reservaRepositorio.countByVueloId(vuelo.getId());
                    int disponibles = vuelo.getCapacidad() - ocupados;

                    return disponibles >= pasajeros;
                })
                .toList();
    }

    public Vuelo actualizarVuelo(Long vueloId, Vuelo vueloActualizado) {

        Vuelo vueloExistente = vueloRepositorio.findById(vueloId)
                .orElseThrow(() -> new RuntimeException("Vuelo no encontrado"));

        boolean cambioHora = !vueloExistente.getHoraSalida().equals(vueloActualizado.getHoraSalida());
        boolean cambioPuerta = !vueloExistente.getPuertaEmbarque().equals(vueloActualizado.getPuertaEmbarque());
        boolean cambioEstado = !vueloExistente.getEstado().equals(vueloActualizado.getEstado());
        boolean cambioAerolinea = !vueloExistente.getAerolinea().equals(vueloActualizado.getAerolinea());

        vueloExistente.setNumeroVuelo(vueloActualizado.getNumeroVuelo());
        vueloExistente.setAerolinea(vueloActualizado.getAerolinea());
        vueloExistente.setHoraSalida(vueloActualizado.getHoraSalida());
        vueloExistente.setPuertaEmbarque(vueloActualizado.getPuertaEmbarque());
        vueloExistente.setEstado(vueloActualizado.getEstado());

        Vuelo vueloGuardado = vueloRepositorio.save(vueloExistente);

        List<Reserva> reservas = reservaRepositorio.findByVueloId(vueloId);

        if (!(cambioHora || cambioPuerta || cambioEstado || cambioAerolinea)) {
            return vueloGuardado;
        }

        // 🧠 MENSAJE PARA BD
        StringBuilder mensaje = new StringBuilder("Actualización en tu vuelo " + vueloGuardado.getNumeroVuelo() + ": ");

        if (cambioHora) {
            mensaje.append("Nueva hora: ").append(vueloGuardado.getHoraSalida()).append(". ");
        }
        if (cambioPuerta) {
            mensaje.append("Nueva puerta: ").append(vueloGuardado.getPuertaEmbarque()).append(". ");
        }
        if (cambioEstado) {
            mensaje.append("Estado: ").append(vueloGuardado.getEstado()).append(". ");
        }
        if (cambioAerolinea) {
            mensaje.append("Aerolínea: ").append(vueloGuardado.getAerolinea()).append(". ");
        }

        String mensajeFinal = mensaje.toString();

        for (Reserva reserva : reservas) {

            String correo = reserva.getPasajero().getCorreo();

            notificacionRepositorio.save(Notificacion.builder()
                    .mensaje(mensajeFinal)
                    .leida(false)
                    .reserva(reserva)
                    .build());

            try {
                emailService.enviarNotificacionVuelo(
                        correo,
                        vueloGuardado,
                        reserva,
                        cambioHora,
                        cambioAerolinea,
                        cambioPuerta,
                        cambioEstado
                );

                System.out.println("[MAIL] Enviado a: " + correo);

            } catch (Exception e) {
                System.err.println("[MAIL] Error enviando a " + correo);
                e.printStackTrace();
            }
        }

        return vueloGuardado;
    }
}