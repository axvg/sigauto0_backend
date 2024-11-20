package com.group.sigautov00.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.group.sigautov00.entity.Cliente;
import com.group.sigautov00.repository.ClienteRepository;

@Service
public class ClienteService {

    @Autowired
    private ClienteRepository clienteRepository;

    public List<Cliente> getAll() {
        return clienteRepository.findAll();
    }

    public Cliente create(Cliente cliente) {
        return clienteRepository.save(cliente);
    }
}
