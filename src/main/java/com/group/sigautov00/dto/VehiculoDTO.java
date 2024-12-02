package com.group.sigautov00.dto;

import lombok.Data;

@Data
public class VehiculoDTO {
    private Long idVehiculo;
    private String marca;
    private String modelo;
    private int year;
    private String placa;
    private Long idCliente;
}
