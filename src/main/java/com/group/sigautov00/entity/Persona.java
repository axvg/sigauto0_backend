package com.group.sigautov00.entity;

import java.time.LocalDate;
import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Builder;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "persona")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Persona {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 100)
    private String nombres;

    @Column(nullable = false, length = 100)
    private String apellidoPaterno;

    @Column(nullable = false, length = 100)
    private String apellidoMaterno;

    @Column(nullable = false)
    private LocalDate fechaNacimiento;

    @Column(nullable = false, length = 200)
    private String direccion;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Genero sexo;

    @Column(nullable = false, unique = true)
    private Long numeroDocumento;

    @Column(unique = true, length = 100)
    private String email;

    @Column(nullable = false)
    private Long telefono;

    @Column(nullable = true, updatable = false)
    private LocalDateTime createdAt = LocalDateTime.now();

    @Column(nullable = true )
    private LocalDateTime updatedAt = LocalDateTime.now();

}
