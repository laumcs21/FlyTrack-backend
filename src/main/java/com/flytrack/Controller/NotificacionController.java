package com.flytrack.Controller;

import com.flytrack.Persistence.Model.Notificacion;
import com.flytrack.Business.Service.NotificacionService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/notificaciones")
@RequiredArgsConstructor
public class NotificacionController {

    private final NotificacionService notificacionServicio;

    @GetMapping("/pasajero/{pasajeroId}")
    public List<Notificacion> obtenerPorPasajero(@PathVariable Long pasajeroId) {
        return notificacionServicio.obtenerPorPasajero(pasajeroId);
    }
}
