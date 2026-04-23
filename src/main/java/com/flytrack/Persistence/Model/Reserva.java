package com.flytrack.Persistence.Model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "reservas")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Reserva {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String codigoReserva;
    private String numeroAsiento;
    private String estadoTiquete;

    @ManyToOne
    @JoinColumn(name = "pasajero_id")
    private Pasajero pasajero;

    @ManyToOne
    @JoinColumn(name = "vuelo_id")
    private Vuelo vuelo;
}