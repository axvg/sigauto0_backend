package com.group.sigautov00.dto;

import java.time.LocalDate;

import lombok.Data;

@Data
public class CitaDTO {
    private Long idCliente;
    private LocalDate fecha;
    private Boolean estado;
}