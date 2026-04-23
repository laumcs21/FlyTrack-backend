package com.flytrack.Persistence.Model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "reportes_equipaje")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ReporteEquipaje {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String descripcion;
    private String estado;

    @ManyToOne
    @JoinColumn(name = "reserva_id")
    private Reserva reserva;
}
