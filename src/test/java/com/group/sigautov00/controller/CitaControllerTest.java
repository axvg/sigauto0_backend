package com.group.sigautov00.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.group.sigautov00.dto.CitaDTO;
import com.group.sigautov00.entity.Cita;
import com.group.sigautov00.entity.Cliente;
import com.group.sigautov00.entity.Genero;
import com.group.sigautov00.entity.Persona;
import com.group.sigautov00.entity.Vehiculo;
import com.group.sigautov00.repository.CitaRepository;
import com.group.sigautov00.repository.ClienteRepository;
import com.group.sigautov00.repository.PersonaRepository;
import com.group.sigautov00.repository.UsuarioRepository;
import com.group.sigautov00.service.CitaService;
import com.group.sigautov00.util.JwtUtil;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.hamcrest.CoreMatchers;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
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

@WebMvcTest(controllers = CitaController.class)
@AutoConfigureMockMvc(addFilters = false)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class CitaControllerTest {
    private final Logger logger=LoggerFactory.getLogger(this.getClass());

    @Autowired
    private MockMvc mockMvc;


    @MockBean
    private CitaService citaService;

    @MockBean
    private JwtUtil jwtUtil;

    @MockBean
    private UsuarioRepository usuarioRepository;

    @MockBean
    private ClienteRepository clienteRepository;

    @MockBean
    private PersonaRepository personaRepository;

    @MockBean
    private CitaRepository citaRepository;

    @Autowired
    private ObjectMapper objectMapper;

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
    @Order(1)
    @DisplayName("Test create cita controller success")
    public void testCreateCita_Success() throws Exception {
        given(citaService.createCita(ArgumentMatchers.any(CitaDTO.class))).willReturn(cita1);

        ResultActions response = mockMvc.perform(post("/api/citas")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(citaDTO)));

        response
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.idCita", CoreMatchers.is(1)))
                .andExpect(jsonPath("$.cliente.idCliente", CoreMatchers.is(1)))
                .andExpect(jsonPath("$.vehiculo.idVehiculo", CoreMatchers.is(1)))
                .andExpect(jsonPath("$.estado", CoreMatchers.is(true)));
    }

    @Test
    @Order(2)
    @DisplayName("Test get citas por cliente controller success")
    public void testGetCitasByCliente_Success() throws Exception {
        Long clienteId = 2L;
        List<Cita> expectedCitas = Arrays.asList(cita2);
        given(citaService.getCitasByCliente(clienteId)).willReturn(expectedCitas);

        ResultActions response = mockMvc.perform(get("/api/citas/cliente/{clienteId}", clienteId)
                .contentType(MediaType.APPLICATION_JSON));

        response
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$[0].idCita", CoreMatchers.is(2)))
                .andExpect(jsonPath("$[0].cliente.idCliente", CoreMatchers.is(2)))
                .andExpect(jsonPath("$[0].vehiculo.idVehiculo", CoreMatchers.is(2)));

        verify(citaService, times(1)).getCitasByCliente(clienteId);
    }

    @Test
    @Order(3)
    @DisplayName("Test get all citas controller success")
    public void testGetCitas_Sucess() throws Exception {
        List<Cita> expectedCitas = Arrays.asList(cita1, cita2, cita3);
        given(citaService.getAllCitas()).willReturn(expectedCitas);

        ResultActions response = mockMvc.perform(get("/api/citas")
                .contentType(MediaType.APPLICATION_JSON));

        assertEquals(expectedCitas.size(), response.andReturn().getResponse().getContentAsString().split("idCita").length - 1);

        response
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$[0].idCita", CoreMatchers.is(1)))
                .andExpect(jsonPath("$[1].idCita", CoreMatchers.is(2)))
                .andExpect(jsonPath("$[2].idCita", CoreMatchers.is(3)));

        verify(citaService, times(1)).getAllCitas();
    }

    @Test
    @Order(4)
    @DisplayName("Test get citas por cliente controller exception")
    public void testGetCitasByCliente_Exception() throws Exception {
        Long clienteId = 3L;
        given(citaService.getCitasByCliente(clienteId)).willThrow(new RuntimeException("Database error"));

        ResultActions response = mockMvc.perform(get("/api/citas/cliente/{clienteId}", clienteId)
                .contentType(MediaType.APPLICATION_JSON));

        response
                .andExpect(status().isInternalServerError());

        verify(citaService, times(1)).getCitasByCliente(clienteId);
    }

    @Test
    @Order(5)
    @DisplayName("Test get all citas controller exception")
    public void testGetAllCitas_Exception() throws Exception {
        given(citaService.getAllCitas()).willThrow(new RuntimeException("Database error"));

        ResultActions response = mockMvc.perform(get("/api/citas")
                .contentType(MediaType.APPLICATION_JSON));

        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR.value(), response.andReturn().getResponse().getStatus());

        verify(citaService, times(1)).getAllCitas();
    }

    @Test
    @Order(6)
    @DisplayName("Test create cita controller exception")
    public void testCreateCita_GeneralException() throws Exception {
        String errorMessage = "Unexpected error";
        given(citaService.createCita(ArgumentMatchers.any(CitaDTO.class))).willThrow(new RuntimeException(errorMessage));

        ResultActions response = mockMvc.perform(post("/api/citas")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(citaDTO)));

        assertEquals(HttpStatus.BAD_REQUEST.value(), response.andReturn().getResponse().getStatus());
    }
}