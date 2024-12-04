package com.group.sigautov00.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.group.sigautov00.dto.CitaDTO;
import com.group.sigautov00.entity.Cita;
import com.group.sigautov00.entity.Cliente;
import com.group.sigautov00.entity.Genero;
import com.group.sigautov00.entity.Persona;
import com.group.sigautov00.entity.Vehiculo;
import com.group.sigautov00.service.CitaService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.hamcrest.CoreMatchers;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

// @WebMvcTest(controllers = CitaController.class)
// @AutoConfigureMockMvc(addFilters = false)
@ExtendWith(MockitoExtension.class)
public class CitaControllerTest {
    private final Logger logger=LoggerFactory.getLogger(this.getClass());

    @Mock
    private CitaService citaService;

    @InjectMocks
    private CitaController citaController;

    private CitaDTO citaDTO;
    private Cita cita1, cita2, cita3;
    private Cliente cliente1, cliente2;
    private Vehiculo vehiculo1, vehiculo2;
    private Persona persona1, persona2;

    @BeforeEach
    public void init() {
        persona1 = Persona.builder()
                .id(1L)
                .nombres("nombre1")
                .apellidoPaterno("ap1")
                .apellidoMaterno("am1")
                .fechaNacimiento(LocalDate.of(1990, 1, 1))
                .direccion("calle 123")
                .sexo(Genero.M)
                .numeroDocumento(123456789L)
                .email("nombre1@mail.com")
                .telefono(1234567890L)
                .createdAt(LocalDateTime.now())
                .updatedAt(LocalDateTime.now())
                .build();

        persona2 = Persona.builder()
                .id(2L)
                .nombres("nombre2")
                .apellidoPaterno("ap2")
                .apellidoMaterno("am2")
                .fechaNacimiento(LocalDate.of(1990, 1, 1))
                .direccion("calle 123")
                .sexo(Genero.F)
                .numeroDocumento(123456789L)
                .email("nombre2@mail.com")
                .telefono(1234567890L)
                .createdAt(LocalDateTime.now())
                .updatedAt(LocalDateTime.now())
                .build();

        cliente1 = Cliente.builder()
                .idCliente(1L)
                .persona(persona1)
                .estado(true)
                .createdAt(LocalDateTime.now())
                .updatedAt(LocalDateTime.now())
                .build();

        cliente2 = Cliente.builder()
                .idCliente(2L)
                .persona(persona2)
                .estado(true)
                .createdAt(LocalDateTime.now())
                .updatedAt(LocalDateTime.now())
                .build();

        vehiculo1 = Vehiculo.builder()
                .idVehiculo(1L)
                .marca("Toyota")
                .modelo("Corolla")
                .year(2022)
                .placa("ABC123")
                .cliente(cliente1)
                .createdAt(LocalDateTime.now())
                .updatedAt(LocalDateTime.now())
                .build();
        
        vehiculo2 = Vehiculo.builder()
                .idVehiculo(2L)
                .marca("Toyota")
                .modelo("Corolla")
                .year(2022)
                .placa("DEF456")
                .cliente(cliente2)
                .createdAt(LocalDateTime.now())
                .updatedAt(LocalDateTime.now())
                .build();

        citaDTO = new CitaDTO();
        citaDTO.setIdCliente(1L);
        citaDTO.setIdVehiculo(1L);
        citaDTO.setIdTipoServicio(1L);
        citaDTO.setFecha(LocalDate.now());
        citaDTO.setEstado(true);

        cita1 = Cita.builder()
                .idCita(1L)
                .cliente(cliente1)
                .vehiculo(vehiculo1)
                .idTipoServicio(1L)
                .fecha(LocalDate.now())
                .estado(true)
                .createdAt(LocalDateTime.now())
                .updatedAt(LocalDateTime.now())
                .build();
        cita2 = Cita.builder()
                .idCita(2L)
                .cliente(cliente2)
                .vehiculo(vehiculo2)
                .idTipoServicio(1L)
                .fecha(LocalDate.now())
                .estado(true)
                .createdAt(LocalDateTime.now())
                .updatedAt(LocalDateTime.now())
                .build();
        cita3 = Cita.builder()
                .idCita(3L)
                .cliente(cliente1)
                .vehiculo(vehiculo1)
                .idTipoServicio(1L)
                .fecha(LocalDate.now())
                .estado(true)
                .createdAt(LocalDateTime.now())
                .updatedAt(LocalDateTime.now())
                .build();
    }

    @Test
    public void testGetAllCitas() throws Exception {
        List<Cita> citas = Arrays.asList(cita1, cita2, cita3);

        logger.info(">Test-GetAllCitas: ");

        when(citaService.getAllCitas()).thenReturn(citas);

        ResponseEntity<List<Cita>> response = citaController.getAllCitas();

        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(citas, response.getBody());
        assertEquals(response.getBody().size(), citas.size());
        verify(citaService, times(1)).getAllCitas();
    }


    @Test
    void testGetAllCitas_Exception() {
        logger.error(">Test-GetAllCitas_Exception: ");

        when(citaService.getAllCitas()).thenThrow(new RuntimeException("Database error"));

        ResponseEntity<List<Cita>> response = citaController.getAllCitas();

        assertNotNull(response);
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
    }


    @Test
    void testGetCitasByCliente_Success() {
        logger.info(">Test-GetCitasByCliente_Success: ");

        Long clienteId = 2L;
        List<Cita> expectedCitas = Arrays.asList(cita2);
        when(citaService.getCitasByCliente(clienteId)).thenReturn(expectedCitas);

        ResponseEntity<List<Cita>> response = citaController.getCitasByCliente(clienteId);

        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(expectedCitas, response.getBody());
        assertEquals(response.getBody().size(), expectedCitas.size());
        verify(citaService, times(1)).getCitasByCliente(clienteId);
    }

    @Test
    void testGetCitasByCliente_Exception() {
        Long clienteId = 3L;
        when(citaService.getCitasByCliente(clienteId)).thenThrow(new RuntimeException("Database error"));

        ResponseEntity<List<Cita>> response = citaController.getCitasByCliente(clienteId);

        assertNotNull(response);
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
    }

    @Test
    void testCreateCita_Success() {
        when(citaService.createCita(citaDTO)).thenReturn(cita1);

        ResponseEntity<?> response = citaController.createCita(citaDTO);

        assertNotNull(response);
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals(cita1.getIdTipoServicio(), ((Cita) response.getBody()).getIdTipoServicio());
        verify(citaService, times(1)).createCita(citaDTO);
    }

    @Test
    void testCreateCita_RuntimeException() {
        String errorMessage = "fecha invalida";
        when(citaService.createCita(citaDTO)).thenThrow(new RuntimeException(errorMessage));

        ResponseEntity<?> response = citaController.createCita(citaDTO);

        assertNotNull(response);
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
    }

    @Test
    void testCreateCita_GeneralException() {
        String errorMessage = "Unexpected error";
        when(citaService.createCita(citaDTO)).thenThrow(new RuntimeException(errorMessage));
    
        ResponseEntity<?> response = citaController.createCita(citaDTO);
    
        assertNotNull(response);
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertEquals("Unexpected error", response.getBody());
    }
}