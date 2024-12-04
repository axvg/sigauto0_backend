package com.group.sigautov00.service;

import com.group.sigautov00.dto.CitaDTO;
import com.group.sigautov00.entity.Cita;
import com.group.sigautov00.entity.Cliente;
import com.group.sigautov00.entity.Genero;
import com.group.sigautov00.entity.Persona;
import com.group.sigautov00.entity.Vehiculo;
import com.group.sigautov00.repository.CitaRepository;
import com.group.sigautov00.repository.ClienteRepository;
import com.group.sigautov00.repository.VehiculoRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class CitaServiceTest {

    @Mock
    private CitaRepository citaRepository;

    @Mock
    private VehiculoRepository vehiculoRepository;

    @Mock
    private ClienteRepository clienteRepository;

    @InjectMocks
    private CitaService citaService;

    private CitaDTO citaDTO;
    private Cita cita;
    private Cliente cliente;
    private Vehiculo vehiculo;

    @BeforeEach
    public void setUp() {
        citaDTO = new CitaDTO();
        citaDTO.setIdCliente(1L);
        citaDTO.setIdVehiculo(1L);
        citaDTO.setFecha(LocalDate.now());
        citaDTO.setEstado(true);
        citaDTO.setIdTipoServicio(1L);

        Persona persona = new Persona();
        persona.setId(1L);
        persona.setNombres("nombre1");
        persona.setApellidoPaterno("ap1");
        persona.setApellidoMaterno("am1");
        persona.setFechaNacimiento(LocalDate.of(1990, 1, 1));
        persona.setDireccion("123 Main St");
        persona.setSexo(Genero.M);
        persona.setNumeroDocumento(123456789L);
        persona.setEmail("nombre1@mail.com");
        persona.setTelefono(1234567890L);

        cliente = new Cliente();
        cliente.setIdCliente(1L);
        cliente.setPersona(persona);
        cliente.setEstado(true);

        vehiculo = new Vehiculo();
        vehiculo.setIdVehiculo(1L);
        vehiculo.setMarca("Toyota");
        vehiculo.setModelo("Corolla");
        vehiculo.setYear(2022);
        vehiculo.setPlaca("ABC123");
        vehiculo.setCliente(cliente);

        cita = new Cita();
        cita.setIdCita(1L);
        cita.setCliente(cliente);
        cita.setVehiculo(vehiculo);
        cita.setFecha(LocalDate.now());
        cita.setEstado(true);
        cita.setIdTipoServicio(1L);
    }

    @Test
    public void testGetAllCitas() {
        List<Cita> citas = Arrays.asList(cita);

        when(citaRepository.findAll()).thenReturn(citas);

        List<Cita> result = citaService.getAllCitas();

        assertThat(result).isNotEmpty();
        assertThat(result.size()).isEqualTo(1);
        assertThat(result.get(0)).isEqualTo(cita);
    }

    @Test
    public void testGetCitasByCliente() {
        List<Cita> citas = Arrays.asList(cita);

        when(citaRepository.findByClienteIdCliente(ArgumentMatchers.anyLong())).thenReturn(citas);

        List<Cita> result = citaService.getCitasByCliente(1L);

        assertThat(result).isNotEmpty();
        assertThat(result.size()).isEqualTo(1);
        assertThat(result.get(0)).isEqualTo(cita);
    }

    @Test
    public void testCreateCita_Success() {
        when(clienteRepository.findById(ArgumentMatchers.anyLong())).thenReturn(Optional.of(cliente));
        when(vehiculoRepository.findById(ArgumentMatchers.anyLong())).thenReturn(Optional.of(vehiculo));
        when(citaRepository.existsByVehiculoAndFecha(ArgumentMatchers.any(Vehiculo.class), ArgumentMatchers.any(LocalDate.class))).thenReturn(false);
        when(citaRepository.save(ArgumentMatchers.any(Cita.class))).thenReturn(cita);

        Cita result = citaService.createCita(citaDTO);

        assertThat(result).isNotNull();
        assertThat(result).isEqualTo(cita);
    }

    @Test
    public void testCreateCita_ClienteNotFound() {
        when(clienteRepository.findById(ArgumentMatchers.anyLong())).thenReturn(Optional.empty());

        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            citaService.createCita(citaDTO);
        });

        assertThat(exception.getMessage()).isEqualTo("Cliente no encontrado");
    }

    @Test
    public void testCreateCita_VehiculoNotFound() {
        when(clienteRepository.findById(ArgumentMatchers.anyLong())).thenReturn(Optional.of(cliente));
        when(vehiculoRepository.findById(ArgumentMatchers.anyLong())).thenReturn(Optional.empty());

        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            citaService.createCita(citaDTO);
        });

        assertThat(exception.getMessage()).isEqualTo("Vehiculo no encontrado");
    }

    @Test
    public void testCreateCita_VehiculoAlreadyHasCita() {
        when(clienteRepository.findById(ArgumentMatchers.anyLong())).thenReturn(Optional.of(cliente));
        when(vehiculoRepository.findById(ArgumentMatchers.anyLong())).thenReturn(Optional.of(vehiculo));
        when(citaRepository.existsByVehiculoAndFecha(ArgumentMatchers.any(Vehiculo.class), ArgumentMatchers.any(LocalDate.class))).thenReturn(true);

        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            citaService.createCita(citaDTO);
        });

        assertThat(exception.getMessage()).isEqualTo("El vehiculo con placa " + vehiculo.getPlaca() + " tiene una cita en la fecha: " + citaDTO.getFecha());
    }
}