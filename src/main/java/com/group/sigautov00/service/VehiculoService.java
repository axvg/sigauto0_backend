package com.group.sigautov00.service;

import java.util.List;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.group.sigautov00.dto.VehiculoDTO;
import com.group.sigautov00.entity.Cliente;
import com.group.sigautov00.entity.Vehiculo;
import com.group.sigautov00.repository.ClienteRepository;
import com.group.sigautov00.repository.VehiculoRepository;

@Service
public class VehiculoService {
    private static final Logger logger = LoggerFactory.getLogger(VehiculoService.class);

    
    @Autowired
    private VehiculoRepository vehiculoRepository;

    @Autowired
    private ClienteRepository clienteRepository;

    public List<VehiculoDTO> getAllVehiculos() {
        return vehiculoRepository.findAll().stream().map(this::convertToDTO).collect(Collectors.toList());
    }

    public VehiculoDTO createVehiculo(VehiculoDTO vehiculoDTO) {
        logger.info("Creating vehiculo with placa: {}", vehiculoDTO.toString());
        try {
            Cliente cliente = clienteRepository.findById(vehiculoDTO.getIdCliente())
                    .orElseThrow(() -> new RuntimeException("Cliente no encontrado"));

            Vehiculo vehiculo = new Vehiculo();
            vehiculo.setMarca(vehiculoDTO.getMarca());
            vehiculo.setModelo(vehiculoDTO.getModelo());
            vehiculo.setYear(vehiculoDTO.getYear());
            vehiculo.setPlaca(vehiculoDTO.getPlaca());
            vehiculo.setCliente(cliente);

            Vehiculo savedVehiculo = vehiculoRepository.save(vehiculo);
            logger.info("Vehiculo creado: {}", savedVehiculo.getIdVehiculo());
            return convertToDTO(savedVehiculo);
        } catch (Exception e) {
            logger.error("Error creando vehiculo", e);
            throw e;
        }
    }

    private VehiculoDTO convertToDTO(Vehiculo vehiculo) {
        VehiculoDTO vehiculoDTO = new VehiculoDTO();
        vehiculoDTO.setMarca(vehiculo.getMarca());
        vehiculoDTO.setModelo(vehiculo.getModelo());
        vehiculoDTO.setYear(vehiculo.getYear());
        vehiculoDTO.setPlaca(vehiculo.getPlaca());
        vehiculoDTO.setIdCliente(vehiculo.getCliente().getIdCliente());
        return vehiculoDTO;
    }
}
