package com.flytrack.Persistence.Repository;

import com.flytrack.Persistence.Model.Reserva;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ReservaRepository extends JpaRepository<Reserva, Long> {
    List<Reserva> findByPasajeroId(Long pasajeroId);

    List<Reserva> findByPasajeroNumeroDocumento(String numeroDocumento);

    List<Reserva> findByVueloId(Long vueloId);

    int countByVueloId(Long vueloId);

    List<Reserva> findByPasajeroCorreo(String correo);}
