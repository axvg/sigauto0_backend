package com.group.sigautov00.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.group.sigautov00.dto.CitaDTO;
import com.group.sigautov00.entity.Cita;
import com.group.sigautov00.service.CitaService;
import com.group.sigautov00.service.VehiculoService;

@RestController
@RequestMapping("/api/citas")
public class CitaController {
    private static final Logger logger = LoggerFactory.getLogger(VehiculoService.class);

    @Autowired
    private CitaService citaService;

    @GetMapping
    public ResponseEntity<List<Cita>> getAllCitas() {
        try {
            List<Cita> citas = citaService.getAllCitas();
            return new ResponseEntity<>(citas, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/cliente/{idCliente}")
    public ResponseEntity<List<Cita>> getCitasByCliente(@PathVariable Long idCliente) {
        try {
            List<Cita> citas = citaService.getCitasByCliente(idCliente);
            return new ResponseEntity<>(citas, HttpStatus.OK);
        } catch (Exception e) {
            logger.error("Error al conseguir citas por cliente id", e);
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping
    public ResponseEntity<?> createCita(@RequestBody CitaDTO citaDTO) {
        logger.warn("Creating cita with fecha: {} -->", citaDTO.toString());

        try {
            logger.warn("using cita service");
            Cita savedCita = citaService.createCita(citaDTO);
            return new ResponseEntity<>(savedCita, HttpStatus.CREATED);
        } catch (RuntimeException e) {
            logger.error("Error creating cita", e);
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            logger.error("Error creando cita", e);
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<String> handleException(Exception e) {
        return new ResponseEntity<>("Error en cita controller: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
