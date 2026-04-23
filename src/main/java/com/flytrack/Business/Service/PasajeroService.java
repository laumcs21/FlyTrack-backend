package com.flytrack.Business.Service;

import com.flytrack.Persistence.Model.Pasajero;
import com.flytrack.Persistence.Repository.PasajeroRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PasajeroService {

    private final PasajeroRepository pasajeroRepositorio;

    public Pasajero guardarPasajero(Pasajero pasajero) {
        return pasajeroRepositorio.save(pasajero);
    }

    public List<Pasajero> obtenerTodos() {
        return pasajeroRepositorio.findAll();
    }

    public Pasajero obtenerPorId(Long id) {
        return pasajeroRepositorio.findById(id)
                .orElseThrow(() -> new RuntimeException("Pasajero no encontrado"));
    }
}
