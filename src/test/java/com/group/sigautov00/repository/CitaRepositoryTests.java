package com.group.sigautov00.repository;

import static org.assertj.core.api.Assertions.assertThat;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.junit.jupiter.api.extension.ExtendWith;

import com.group.sigautov00.entity.Cita;
import com.group.sigautov00.entity.Cliente;
import com.group.sigautov00.entity.Genero;
import com.group.sigautov00.entity.Persona;
import com.group.sigautov00.entity.TipoServicio;
import com.group.sigautov00.entity.Vehiculo;

@DataJpaTest
@AutoConfigureTestDatabase(replace = Replace.NONE)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class CitaRepositoryTests {

    @Autowired
    private CitaRepository citaRepository;

    @Autowired
    private ClienteRepository clienteRepository;

    @Autowired
    private VehiculoRepository vehiculoRepository;

    @Autowired
    private PersonaRepository personaRepository;

    private Cliente cliente;
    private Vehiculo vehiculo;
    private TipoServicio tipoServicio;

    @BeforeEach
    public void setUp() {
        Persona persona = Persona.builder()
                .nombres("nombre")
                .apellidoPaterno("ap")
                .apellidoMaterno("am")
                .fechaNacimiento(LocalDate.of(1990, 1, 1))
                .direccion("calle 123")
                .sexo(Genero.M)
                .numeroDocumento(123456789L)
                .email("nombre@mail.com")
                .telefono(1234567890L)
                .build();
        persona = personaRepository.save(persona);
        
        cliente = Cliente.builder()
                .persona(persona)
                .estado(true)
                .createdAt(LocalDateTime.now())
                .updatedAt(LocalDateTime.now())
                .build();
        cliente = clienteRepository.save(cliente);
        
        vehiculo = Vehiculo.builder()
                .marca("Toyota")
                .modelo("Corolla")
                .year(2022)
                .placa("ABC124")
                .cliente(cliente)
                .createdAt(LocalDateTime.now())
                .updatedAt(LocalDateTime.now())
                .build();
        vehiculo = vehiculoRepository.save(vehiculo);
        
        tipoServicio = TipoServicio.builder()
                .nombre("Mantenimiento")
                .precio(new BigDecimal("100.00"))
                .build();
    }

    @Test
    @Order(1)
    @Rollback(value = true)
    @DisplayName("Test para verificar si existe una cita por cliente")
    public void testFindByClienteIdCliente() {
        Cita cita = new Cita();
        cita.setCliente(cliente);
        cita.setVehiculo(vehiculo);
        cita.setIdTipoServicio(1L);
        cita.setFecha(LocalDate.now());
        cita.setEstado(true);
        cita.setCreatedAt(LocalDateTime.now());
        cita.setUpdatedAt(LocalDateTime.now());
        
        citaRepository.save(cita);

        List<Cita> citas = citaRepository.findByClienteIdCliente(cliente.getIdCliente());
        assertThat(citas).isNotEmpty();
        assertThat(citas.get(0).getCliente().getIdCliente()).isEqualTo(cliente.getIdCliente());
    }

    @Test
    @Order(2)
    @Rollback(value = true)
    @DisplayName("Test para verificar si existe una cita por vehiculo y fecha")
    public void testExistsByVehiculoAndFecha() {
        LocalDate fecha = LocalDate.now();
        Cita cita = new Cita();
        cita.setCliente(cliente);
        cita.setVehiculo(vehiculo);
        cita.setIdTipoServicio(2L);
        cita.setFecha(fecha);
        cita.setEstado(true);
        cita.setCreatedAt(LocalDateTime.now());
        cita.setUpdatedAt(LocalDateTime.now());
        citaRepository.save(cita);
        

        boolean exists = citaRepository.existsByVehiculoAndFecha(vehiculo, fecha);
        assertThat(exists).isTrue();
    }
}