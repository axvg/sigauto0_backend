package com.group.sigautov00.repository;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.group.sigautov00.entity.Cita;
import com.group.sigautov00.entity.Vehiculo;

@Repository
public interface CitaRepository extends JpaRepository<Cita, Long> {
    List<Cita> findByClienteIdCliente(Long idCliente);

    boolean existsByVehiculoAndFecha(Vehiculo vehiculo, LocalDate fecha);
}