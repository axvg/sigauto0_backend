package com.group.sigautov00.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.group.sigautov00.entity.Vehiculo;

@Repository
public interface VehiculoRepository extends JpaRepository<Vehiculo, Long> {
}