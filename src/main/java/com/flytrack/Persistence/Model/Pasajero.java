package com.flytrack.Persistence.Model;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

@Entity
@Table(name = "pasajeros")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Pasajero {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nombreCompleto;

    @Column (unique = true,  nullable = false)
    private String numeroDocumento;

    private String correo;
    private LocalDate fechaNacimiento;
}
