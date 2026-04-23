package com.flytrack.Persistence.Repository;

import com.flytrack.Persistence.Model.CodigoVerificacion;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CodigoVerificacionRepository extends JpaRepository<CodigoVerificacion, Long> {

    Optional<CodigoVerificacion> findTopByEmailOrderByIdDesc(String email);
}
