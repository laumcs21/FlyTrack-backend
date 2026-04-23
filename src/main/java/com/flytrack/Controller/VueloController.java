package com.flytrack.Controller;

import com.flytrack.Persistence.Model.Vuelo;
import com.flytrack.Business.Service.VueloService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/vuelos")
@RequiredArgsConstructor
public class VueloController {

    private final VueloService vueloServicio;

    @PostMapping
    public Vuelo crearVuelo(@RequestBody Vuelo vuelo) {
        return vueloServicio.guardarVuelo(vuelo);
    }

    @GetMapping
    public List<Vuelo> obtenerTodos() {
        return vueloServicio.obtenerTodos();
    }

    @GetMapping("/{id}")
    public Vuelo obtenerPorId(@PathVariable Long id) {
        return vueloServicio.obtenerPorId(id);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Vuelo> actualizarVuelo(
            @PathVariable Long id,
            @RequestBody Vuelo vueloActualizado) {
        Vuelo vuelo = vueloServicio.actualizarVuelo(id, vueloActualizado);
        return ResponseEntity.ok(vuelo);
        }

    @DeleteMapping("/{id}")
    public void eliminarVuelo(@PathVariable Long id) {
        vueloServicio.eliminarVuelo(id);
    }

    @GetMapping("/filtrar")
    public List<Vuelo> filtrar(
            @RequestParam String origen,
            @RequestParam String destino,
            @RequestParam String fecha,
            @RequestParam int adultos,
            @RequestParam int ninos
    ) {
        int totalPasajeros = adultos + ninos;

        return vueloServicio.filtrarVuelos(
                origen,
                destino,
                LocalDate.parse(fecha),
                totalPasajeros
        );
    }

}
