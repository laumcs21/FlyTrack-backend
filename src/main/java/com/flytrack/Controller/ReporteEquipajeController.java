package com.flytrack.Controller;

import com.flytrack.Business.Dto.ReporteEquipajePeticion;
import com.flytrack.Persistence.Model.ReporteEquipaje;
import com.flytrack.Business.Service.ReporteEquipajeService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/reportes-equipaje")
@RequiredArgsConstructor
public class ReporteEquipajeController {

    private final ReporteEquipajeService reporteEquipajeServicio;

    @PostMapping
    public ReporteEquipaje crearReporte(@RequestBody ReporteEquipajePeticion peticion) {
        return reporteEquipajeServicio.crearReporte(
                peticion.getReservaId(),
                peticion.getDescripcion()
        );
    }

    @GetMapping("/pasajero/{pasajeroId}")
    public List<ReporteEquipaje> obtenerPorPasajero(@PathVariable Long pasajeroId) {
        return reporteEquipajeServicio.obtenerPorPasajero(pasajeroId);
    }
}
