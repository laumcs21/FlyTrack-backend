package com.flytrack.Persistence.Repository;

import com.flytrack.Persistence.Model.Pasajero;
import jakarta.validation.constraints.NotBlank;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PasajeroRepository extends JpaRepository<Pasajero, Long> {
    boolean existsByNumeroDocumento(String documento);

    Optional<Pasajero> findByNumeroDocumento(@NotBlank(message = "El documento es obligatorio") String documento);
}