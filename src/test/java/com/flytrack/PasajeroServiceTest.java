package com.flytrack;

import com.flytrack.Business.Service.PasajeroService;
import com.flytrack.Persistence.Model.Pasajero;
import com.flytrack.Persistence.Repository.PasajeroRepository;
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
class PasajeroServiceTest {

    @Mock
    private PasajeroRepository pasajeroRepositorio;

    @InjectMocks
    private PasajeroService pasajeroService;

    private Pasajero pasajeroPrueba;

    @BeforeEach
    void setUp() {
        pasajeroPrueba = Pasajero.builder()
                .id(1L)
                .nombreCompleto("Juan Pérez")
                .numeroDocumento("123456789")
                .correo("juan@email.com")
                .fechaNacimiento(LocalDate.of(1990, 5, 20))
                .build();
    }

    @Test
    void guardarPasajero_debeRetornarPasajeroGuardado() {
        when(pasajeroRepositorio.save(pasajeroPrueba)).thenReturn(pasajeroPrueba);
        Pasajero resultado = pasajeroService.guardarPasajero(pasajeroPrueba);
        assertNotNull(resultado);
        assertEquals("Juan Pérez", resultado.getNombreCompleto());
        verify(pasajeroRepositorio, times(1)).save(pasajeroPrueba);
    }

    @Test
    void obtenerTodos_debeRetornarListaDePasajeros() {
        when(pasajeroRepositorio.findAll()).thenReturn(List.of(pasajeroPrueba));
        List<Pasajero> resultado = pasajeroService.obtenerTodos();
        assertEquals(1, resultado.size());
    }

    @Test
    void obtenerTodos_sinPasajeros_debeRetornarListaVacia() {
        when(pasajeroRepositorio.findAll()).thenReturn(List.of());
        List<Pasajero> resultado = pasajeroService.obtenerTodos();
        assertTrue(resultado.isEmpty());
    }

    @Test
    void obtenerPorId_cuandoExiste_debeRetornarPasajero() {
        when(pasajeroRepositorio.findById(1L)).thenReturn(Optional.of(pasajeroPrueba));
        Pasajero resultado = pasajeroService.obtenerPorId(1L);
        assertNotNull(resultado);
        assertEquals("juan@email.com", resultado.getCorreo());
    }

    @Test
    void obtenerPorId_cuandoNoExiste_debeLanzarExcepcion() {
        when(pasajeroRepositorio.findById(99L)).thenReturn(Optional.empty());
        RuntimeException ex = assertThrows(RuntimeException.class, () -> pasajeroService.obtenerPorId(99L));
        assertEquals("Pasajero no encontrado", ex.getMessage());
    }
}
