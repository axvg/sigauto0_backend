package com.group.sigautov00.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.group.sigautov00.entity.Cita;

@Repository
public interface CitaRepository extends JpaRepository<Cita, Long> {
}