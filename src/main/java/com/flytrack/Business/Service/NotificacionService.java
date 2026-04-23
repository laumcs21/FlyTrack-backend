package com.flytrack.Business.Service;

import com.flytrack.Persistence.Model.Notificacion;
import com.flytrack.Persistence.Repository.NotificacionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class NotificacionService {

    private final NotificacionRepository notificacionRepositorio;

    public List<Notificacion> obtenerPorPasajero(Long pasajeroId) {
        return notificacionRepositorio.findByReservaPasajeroId(pasajeroId);
    }
}
