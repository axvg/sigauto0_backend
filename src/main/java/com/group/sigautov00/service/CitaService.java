package com.group.sigautov00.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.group.sigautov00.dto.CitaDTO;
import com.group.sigautov00.entity.Cita;
import com.group.sigautov00.entity.Cliente;
import com.group.sigautov00.repository.CitaRepository;
import com.group.sigautov00.repository.ClienteRepository;

@Service
public class CitaService {

    @Autowired
    private CitaRepository citaRepository;

    @Autowired
    private ClienteRepository clienteRepository;

    public List<Cita> getAllCitas() {
        return citaRepository.findAll();
    }

    public Cita createCita(CitaDTO citaDTO) {
        Cliente cliente = clienteRepository.findById(citaDTO.getIdCliente())
                .orElseThrow(() -> new RuntimeException("Cliente no encontrado"));

        Cita cita = new Cita();
        cita.setCliente(cliente);
        cita.setFecha(citaDTO.getFecha());
        cita.setEstado(citaDTO.getEstado());

        return citaRepository.save(cita);
    }
}
