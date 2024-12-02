package com.group.sigautov00.service;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.group.sigautov00.dto.CitaDTO;
import com.group.sigautov00.entity.Cita;
import com.group.sigautov00.entity.Cliente;
import com.group.sigautov00.entity.Vehiculo;
import com.group.sigautov00.repository.CitaRepository;
import com.group.sigautov00.repository.ClienteRepository;
import com.group.sigautov00.repository.VehiculoRepository;

@Service
public class CitaService {
    private static final Logger logger = LoggerFactory.getLogger(VehiculoService.class);

    @Autowired
    private CitaRepository citaRepository;

    @Autowired
    private VehiculoRepository vehiculoRepository;

    @Autowired
    private ClienteRepository clienteRepository;

    public List<Cita> getAllCitas() {
        return citaRepository.findAll();
    }

    public List<Cita> getCitasByCliente(Long idCliente) {
        return citaRepository.findByClienteIdCliente(idCliente);
    }

    public Cita createCita(CitaDTO citaDTO) {
        logger.warn("Creating cita with fecha: {}", citaDTO.toString());

        Cliente cliente = clienteRepository.findById(citaDTO.getIdCliente())
                .orElseThrow(() -> new RuntimeException("Cliente no encontrado"));

        Vehiculo vehiculo = vehiculoRepository.findById(citaDTO.getIdVehiculo())
                .orElseThrow(() -> new RuntimeException("Vehiculo no encontrado"));

        boolean exists = citaRepository.existsByVehiculoAndFecha(vehiculo, citaDTO.getFecha());
        if (exists) {
            throw new RuntimeException("El vehiculo con placa " + vehiculo.getPlaca() + " tiene una cita en la fecha: " + citaDTO.getFecha());
        }
        
        Cita cita = new Cita();
        cita.setCliente(cliente);
        cita.setVehiculo(vehiculo);
        cita.setFecha(citaDTO.getFecha());
        cita.setEstado(citaDTO.getEstado());
        cita.setIdTipoServicio(citaDTO.getIdTipoServicio());

        return citaRepository.save(cita);
    }
}
