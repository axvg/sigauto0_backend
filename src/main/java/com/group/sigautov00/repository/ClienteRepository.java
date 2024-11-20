package com.group.sigautov00.repository;
import org.springframework.data.jpa.repository.JpaRepository;

import com.group.sigautov00.entity.Cliente;

public interface ClienteRepository extends JpaRepository<Cliente, Long> {
}