package com.flytrack.Controller;

import com.flytrack.Persistence.Model.Pasajero;
import com.flytrack.Business.Service.PasajeroService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/pasajeros")
@RequiredArgsConstructor
public class PasajeroController {

    private final PasajeroService pasajeroServicio;

    @PostMapping
    public Pasajero crearPasajero(@RequestBody Pasajero pasajero) {
        return pasajeroServicio.guardarPasajero(pasajero);
    }

    @GetMapping
    public List<Pasajero> obtenerTodos() {
        return pasajeroServicio.obtenerTodos();
    }

    @GetMapping("/{id}")
    public Pasajero obtenerPorId(@PathVariable Long id) {
        return pasajeroServicio.obtenerPorId(id);
    }
}
