package com.flytrack.Persistence.Mapper;

import com.flytrack.Business.Dto.ReservaDto;
import com.flytrack.Persistence.Model.Reserva;
import org.springframework.stereotype.Component;

@Component
public class ReservaMapper {

    public ReservaDto toDTO(Reserva reserva) {

        return new ReservaDto(
                reserva.getId(),
                reserva.getCodigoReserva(),
                reserva.getNumeroAsiento(),
                reserva.getEstadoTiquete(),

                // vuelo
                reserva.getVuelo().getOrigen(),
                reserva.getVuelo().getDestino(),
                reserva.getVuelo().getFecha(),

                // pasajero
                reserva.getPasajero().getNombreCompleto()
        );
    }
}
