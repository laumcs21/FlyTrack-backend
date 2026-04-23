package com.flytrack.Persistence.Repository;

import com.flytrack.Persistence.Model.Pasajero;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PasajeroRepository extends JpaRepository<Pasajero, Long> {
    boolean existsByNumeroDocumento(String documento);
}