package com.group.sigautov00.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.group.sigautov00.entity.Persona;
import com.group.sigautov00.repository.PersonaRepository;

@Service
public class PersonaService {
    @Autowired
    private PersonaRepository personaRepository;

    public List<Persona> getAll() {
        return personaRepository.findAll();
    }

    public Persona createPersona(Persona persona) {
        return personaRepository.save(persona);
    }
}
