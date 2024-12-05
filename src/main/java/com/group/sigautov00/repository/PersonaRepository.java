package com.group.sigautov00.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.group.sigautov00.entity.Persona;

@Repository
public interface PersonaRepository extends JpaRepository<Persona, Long>  {
    boolean existsByEmail(String email);
    boolean existsByNumeroDocumento(Long numeroDocumento);
}
