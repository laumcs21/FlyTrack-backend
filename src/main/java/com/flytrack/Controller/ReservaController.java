package com.flytrack.Controller;

import com.flytrack.Business.Dto.ReservaDto;
import com.flytrack.Business.Dto.ReservaRequest;
import com.flytrack.Persistence.Mapper.ReservaMapper;
import com.flytrack.Persistence.Model.Reserva;
import com.flytrack.Business.Service.ReservaService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import jakarta.validation.Valid;


import java.util.List;

@RestController
@RequestMapping("/api/reservas")
@RequiredArgsConstructor
public class ReservaController {

    private final ReservaService reservaServicio;
    private final ReservaMapper reservaMapper;
    @PostMapping
    public List<ReservaDto> crearReserva(@Valid @RequestBody ReservaRequest request) {
        return reservaServicio.crearReserva(request).stream().map(reservaMapper::toDTO).toList();
    }

    @GetMapping("/pasajero/{pasajeroId}")
    public List<Reserva> obtenerReservasPorPasajero(@PathVariable Long pasajeroId) {
        return reservaServicio.obtenerReservasPorPasajero(pasajeroId);
    }

    @GetMapping("/{reservaId}")
    public Reserva obtenerReservaPorId(@PathVariable Long reservaId) {
        return reservaServicio.obtenerReservaPorId(reservaId);
    }
}
