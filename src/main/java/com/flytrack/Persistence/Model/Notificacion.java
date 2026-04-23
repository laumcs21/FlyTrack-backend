package com.flytrack.Persistence.Model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "notificaciones")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Notificacion {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String mensaje;
    private boolean leida;

    @ManyToOne
    @JoinColumn(name = "reserva_id")
    private Reserva reserva;
}
