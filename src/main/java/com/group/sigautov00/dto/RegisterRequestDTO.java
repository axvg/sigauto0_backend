package com.group.sigautov00.dto;

import java.time.LocalDate;

import lombok.Data;

@Data
public class RegisterRequestDTO {
    private String email;
    private String password;
    private String nombres;
    private String apellidoPaterno;
    private String apellidoMaterno;
    private String direccion;
    private LocalDate fechaNacimiento; // why we use local date instead of date ?
    // we use LocalDate instead of Date because Date is deprecated in Java 8
    private String sexo;
    private Long numeroDocumento;
    private Long telefono;
}
