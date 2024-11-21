package com.group.sigautov00.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.group.sigautov00.dto.VehiculoDTO;
import com.group.sigautov00.service.VehiculoService;


@RestController
@RequestMapping("/api/vehiculos")
public class VehiculoController {
    private static final Logger logger = LoggerFactory.getLogger(VehiculoService.class);

    @Autowired
    private VehiculoService vehiculoService;

    @GetMapping
    public ResponseEntity<List<VehiculoDTO>> getAllVehiculos() {
        try {
            List<VehiculoDTO> vehiculos = vehiculoService.getAllVehiculos();
            return new ResponseEntity<>(vehiculos, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping
    public ResponseEntity<VehiculoDTO> createVehiculo(@RequestBody VehiculoDTO vehiculoDTO) {
        logger.info("Creating vehiculo with placa: {} -->", vehiculoDTO);
        try {
            VehiculoDTO newVehiculo = vehiculoService.createVehiculo(vehiculoDTO);
            return new ResponseEntity<>(newVehiculo, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<String> handleException(Exception e) {
        return new ResponseEntity<>("Error: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
