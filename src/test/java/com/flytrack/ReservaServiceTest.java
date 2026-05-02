package com.flytrack;

import com.flytrack.Business.Dto.PasajeroDTO;
import com.flytrack.Business.Dto.ReservaRequest;
import com.flytrack.Business.Service.ReservaService;
import com.flytrack.Persistence.Mapper.PasajeroMapper;
import com.flytrack.Persistence.Model.Pasajero;
import com.flytrack.Persistence.Model.Reserva;
import com.flytrack.Persistence.Model.Vuelo;
import com.flytrack.Persistence.Repository.PasajeroRepository;
import com.flytrack.Persistence.Repository.ReservaRepository;
import com.flytrack.Persistence.Repository.VueloRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ReservaServiceTest {

    @Mock private ReservaRepository reservaRepositorio;
    @Mock private PasajeroRepository pasajeroRepositorio;
    @Mock private VueloRepository vueloRepositorio;
    @Mock private PasajeroMapper pasajeroMapper;

    @InjectMocks
    private ReservaService reservaService;

    private Vuelo vuelo;
    private PasajeroDTO pasajeroDTO;
    private Pasajero pasajero;

    @BeforeEach
    void setUp() {
        vuelo = new Vuelo();
        vuelo.setId(1L);
        vuelo.setNumeroVuelo("AV101");
        vuelo.setCapacidad(150);

        pasajeroDTO = new PasajeroDTO();
        pasajeroDTO.setNombre("María López");
        pasajeroDTO.setDocumento("987654321");
        pasajeroDTO.setEmail("maria@email.com");
        pasajeroDTO.setFechaNacimiento(LocalDate.of(1995, 3, 10));

        pasajero = Pasajero.builder()
                .id(1L)
                .nombreCompleto("María López")
                .numeroDocumento("987654321")
                .correo("maria@email.com")
                .build();
    }

    @Test
    void crearReserva_conPasajeroNuevo_debeCrearReserva() {
        ReservaRequest request = new ReservaRequest();
        request.setVueloId(1L);
        request.setPasajeros(List.of(pasajeroDTO));

        when(vueloRepositorio.findById(1L)).thenReturn(Optional.of(vuelo));
        when(reservaRepositorio.findByVueloId(1L)).thenReturn(List.of());
        when(pasajeroRepositorio.findByNumeroDocumento("987654321")).thenReturn(Optional.empty());
        when(pasajeroMapper.toEntity(pasajeroDTO)).thenReturn(pasajero);
        when(pasajeroRepositorio.save(any())).thenReturn(pasajero);
        when(reservaRepositorio.save(any())).thenAnswer(inv -> inv.getArgument(0));

        List<Reserva> resultado = reservaService.crearReserva(request);

        assertEquals(1, resultado.size());
        assertEquals("CONFIRMADO", resultado.get(0).getEstadoTiquete());
    }

    @Test
    void crearReserva_cuandoVueloNoExiste_debeLanzarExcepcion() {
        ReservaRequest request = new ReservaRequest();
        request.setVueloId(99L);
        request.setPasajeros(List.of(pasajeroDTO));

        when(vueloRepositorio.findById(99L)).thenReturn(Optional.empty());

        RuntimeException ex = assertThrows(RuntimeException.class, () -> reservaService.crearReserva(request));
        assertEquals("Vuelo no encontrado", ex.getMessage());
    }

    @Test
    void crearReserva_conDocumentoDuplicado_debeLanzarExcepcion() {
        Reserva reservaExistente = Reserva.builder()
                .numeroAsiento("5")
                .pasajero(pasajero)
                .vuelo(vuelo)
                .build();

        ReservaRequest request = new ReservaRequest();
        request.setVueloId(1L);
        request.setPasajeros(List.of(pasajeroDTO));

        when(vueloRepositorio.findById(1L)).thenReturn(Optional.of(vuelo));
        when(reservaRepositorio.findByVueloId(1L)).thenReturn(List.of(reservaExistente));

        RuntimeException ex = assertThrows(RuntimeException.class, () -> reservaService.crearReserva(request));
        assertTrue(ex.getMessage().contains("ya tiene una reserva en este vuelo"));
    }

    @Test
    void crearReserva_cuandoVueloLleno_debeLanzarExcepcion() {
        vuelo.setCapacidad(1);

        Pasajero otroPasajero = Pasajero.builder().numeroDocumento("111111111").build();
        Reserva reservaExistente = Reserva.builder()
                .numeroAsiento("1")
                .pasajero(otroPasajero)
                .vuelo(vuelo)
                .build();

        ReservaRequest request = new ReservaRequest();
        request.setVueloId(1L);
        request.setPasajeros(List.of(pasajeroDTO));

        when(vueloRepositorio.findById(1L)).thenReturn(Optional.of(vuelo));
        when(reservaRepositorio.findByVueloId(1L)).thenReturn(List.of(reservaExistente));

        RuntimeException ex = assertThrows(RuntimeException.class, () -> reservaService.crearReserva(request));
        assertEquals("No hay suficientes asientos disponibles", ex.getMessage());
    }

    @Test
    void obtenerReservaPorId_cuandoExiste_debeRetornarReserva() {
        Reserva reserva = Reserva.builder().id(1L).codigoReserva("RES-001").estadoTiquete("CONFIRMADO").build();
        when(reservaRepositorio.findById(1L)).thenReturn(Optional.of(reserva));

        Reserva resultado = reservaService.obtenerReservaPorId(1L);
        assertEquals("RES-001", resultado.getCodigoReserva());
    }

    @Test
    void obtenerReservaPorId_cuandoNoExiste_debeLanzarExcepcion() {
        when(reservaRepositorio.findById(99L)).thenReturn(Optional.empty());
        RuntimeException ex = assertThrows(RuntimeException.class, () -> reservaService.obtenerReservaPorId(99L));
        assertEquals("Reserva no encontrada", ex.getMessage());
    }

    @Test
    void obtenerReservasPorPasajero_debeRetornarLista() {
        Reserva reserva = Reserva.builder().id(1L).estadoTiquete("CONFIRMADO").pasajero(pasajero).build();
        when(reservaRepositorio.findByPasajeroId(1L)).thenReturn(List.of(reserva));

        List<Reserva> resultado = reservaService.obtenerReservasPorPasajero(1L);
        assertEquals(1, resultado.size());
    }
}
