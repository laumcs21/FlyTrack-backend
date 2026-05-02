package com.flytrack;

import com.flytrack.Business.Service.EmailService;
import com.flytrack.Business.Service.VueloService;
import com.flytrack.Persistence.Model.Vuelo;
import com.flytrack.Persistence.Repository.NotificacionRepository;
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
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class VueloServiceTest {

    @Mock private VueloRepository vueloRepositorio;
    @Mock private ReservaRepository reservaRepositorio;
    @Mock private NotificacionRepository notificacionRepositorio;
    @Mock private EmailService emailService;

    @InjectMocks
    private VueloService vueloService;

    private Vuelo vueloPrueba;

    @BeforeEach
    void setUp() {
        vueloPrueba = new Vuelo();
        vueloPrueba.setId(1L);
        vueloPrueba.setNumeroVuelo("AV101");
        vueloPrueba.setAerolinea("Avianca");
        vueloPrueba.setHoraSalida("08:00");
        vueloPrueba.setPuertaEmbarque("A3");
        vueloPrueba.setEstado("ACTIVO");
        vueloPrueba.setCapacidad(150);
        vueloPrueba.setFecha(LocalDate.of(2026, 6, 15));
    }

    @Test
    void guardarVuelo_debeRetornarVueloGuardado() {
        when(vueloRepositorio.save(vueloPrueba)).thenReturn(vueloPrueba);
        Vuelo resultado = vueloService.guardarVuelo(vueloPrueba);
        assertNotNull(resultado);
        assertEquals("AV101", resultado.getNumeroVuelo());
        verify(vueloRepositorio, times(1)).save(vueloPrueba);
    }

    @Test
    void obtenerPorId_cuandoExiste_debeRetornarVuelo() {
        when(vueloRepositorio.findById(1L)).thenReturn(Optional.of(vueloPrueba));
        Vuelo resultado = vueloService.obtenerPorId(1L);
        assertNotNull(resultado);
        assertEquals("Avianca", resultado.getAerolinea());
    }

    @Test
    void obtenerPorId_cuandoNoExiste_debeLanzarExcepcion() {
        when(vueloRepositorio.findById(99L)).thenReturn(Optional.empty());
        RuntimeException ex = assertThrows(RuntimeException.class, () -> vueloService.obtenerPorId(99L));
        assertEquals("Vuelo no encontrado", ex.getMessage());
    }

    @Test
    void obtenerTodos_debeRetornarListaDeVuelos() {
        when(vueloRepositorio.findAll()).thenReturn(List.of(vueloPrueba));
        List<Vuelo> resultado = vueloService.obtenerTodos();
        assertEquals(1, resultado.size());
    }

    @Test
    void eliminarVuelo_cuandoExiste_debeEliminar() {
        when(vueloRepositorio.findById(1L)).thenReturn(Optional.of(vueloPrueba));
        vueloService.eliminarVuelo(1L);
        verify(vueloRepositorio, times(1)).delete(vueloPrueba);
    }

    @Test
    void eliminarVuelo_cuandoNoExiste_debeLanzarExcepcion() {
        when(vueloRepositorio.findById(99L)).thenReturn(Optional.empty());
        assertThrows(RuntimeException.class, () -> vueloService.eliminarVuelo(99L));
    }

    @Test
    void filtrarVuelos_conAsientosDisponibles_debeRetornarVuelos() {
        when(vueloRepositorio.findByOrigenAndDestinoAndFecha("Bogotá", "Medellín", LocalDate.of(2026, 6, 15)))
                .thenReturn(List.of(vueloPrueba));
        when(reservaRepositorio.countByVueloId(1L)).thenReturn(10);
        List<Vuelo> resultado = vueloService.filtrarVuelos("Bogotá", "Medellín", LocalDate.of(2026, 6, 15), 2);
        assertEquals(1, resultado.size());
    }

    @Test
    void filtrarVuelos_sinAsientosDisponibles_debeRetornarListaVacia() {
        when(vueloRepositorio.findByOrigenAndDestinoAndFecha("Bogotá", "Medellín", LocalDate.of(2026, 6, 15)))
                .thenReturn(List.of(vueloPrueba));
        when(reservaRepositorio.countByVueloId(1L)).thenReturn(150);
        List<Vuelo> resultado = vueloService.filtrarVuelos("Bogotá", "Medellín", LocalDate.of(2026, 6, 15), 1);
        assertTrue(resultado.isEmpty());
    }
}