package com.group.sigautov00.entity;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "vehiculo")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Vehiculo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idVehiculo;

    @Column(nullable = false, length = 50)
    private String marca;

    @Column(nullable = false, length = 50)
    private String modelo;

    @Column(nullable = false)
    private int year;

    @Column(nullable = false, length = 20, unique = true)
    private String placa;

    @ManyToOne
    @JoinColumn(name = "id_cliente", nullable = false)
    private Cliente cliente;

    @Column(nullable = false, updatable = false)
    private LocalDateTime createdAt = LocalDateTime.now();

    @Column(nullable = false)
    private LocalDateTime updatedAt = LocalDateTime.now();
}
