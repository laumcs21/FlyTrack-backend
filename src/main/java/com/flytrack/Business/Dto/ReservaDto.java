package com.flytrack.Business.Dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDate;

@Data
@AllArgsConstructor
public class ReservaDto {

    private Long id;
    private String codigoReserva;
    private String numeroAsiento;
    private String estadoTiquete;

    // vuelo
    private String origen;
    private String destino;
    private LocalDate fecha;

    // pasajero
    private String nombrePasajero;
}