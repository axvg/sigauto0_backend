package com.group.sigautov00.repository;

import static org.assertj.core.api.Assertions.assertThat;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.junit.jupiter.api.extension.ExtendWith;

import com.group.sigautov00.entity.Cita;
import com.group.sigautov00.entity.Cliente;
import com.group.sigautov00.entity.Genero;
import com.group.sigautov00.entity.Persona;
import com.group.sigautov00.entity.TipoServicio;
import com.group.sigautov00.entity.Vehiculo;

@ExtendWith(SpringExtension.class)
@DataJpaTest
@AutoConfigureTestDatabase(replace = Replace.NONE)
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
        Persona persona = new Persona();
        persona.setNombres("nombre");
        persona.setApellidoPaterno("ap");
        persona.setApellidoMaterno("am");
        persona.setFechaNacimiento(LocalDate.of(1990, 1, 1));
        persona.setDireccion("calle 123");
        persona.setSexo(Genero.M);
        persona.setNumeroDocumento(123456789L);
        persona.setEmail("nombre@mail.com");
        persona.setTelefono(1234567890L);
        persona = personaRepository.save(persona);

        cliente = new Cliente();
        cliente.setPersona(persona);
        cliente.setEstado(true);
        cliente = clienteRepository.save(cliente);

        vehiculo = new Vehiculo();
        vehiculo.setMarca("Toyota");
        vehiculo.setModelo("Corolla");
        vehiculo.setYear(2022);
        vehiculo.setPlaca("ABC124");
        vehiculo.setCliente(cliente);
        vehiculo = vehiculoRepository.save(vehiculo);

        tipoServicio = new TipoServicio();
        tipoServicio.setNombre("Mantenimiento");
        tipoServicio.setPrecio(new BigDecimal("100.00"));
    }

    @Test
    public void testFindByClienteIdCliente() {
        Cita cita = new Cita();
        cita.setCliente(cliente);
        cita.setVehiculo(vehiculo);
        cita.setIdTipoServicio(1L);
        cita.setFecha(LocalDate.now());
        cita.setEstado(true);
        citaRepository.save(cita);

        List<Cita> citas = citaRepository.findByClienteIdCliente(cliente.getIdCliente());
        assertThat(citas).isNotEmpty();
        assertThat(citas.get(0).getCliente().getIdCliente()).isEqualTo(cliente.getIdCliente());
    }

    @Test
    public void testExistsByVehiculoAndFecha() {
        LocalDate fecha = LocalDate.now();
        Cita cita = new Cita();
        cita.setCliente(cliente);
        cita.setVehiculo(vehiculo);
        cita.setIdTipoServicio(2L);
        cita.setFecha(fecha);
        cita.setEstado(true);
        citaRepository.save(cita);

        boolean exists = citaRepository.existsByVehiculoAndFecha(vehiculo, fecha);
        assertThat(exists).isTrue();
    }
}