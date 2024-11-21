package com.group.sigautov00.service;

import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.group.sigautov00.dto.AuthResponseDTO;
import com.group.sigautov00.dto.LoginRequestDTO;
import com.group.sigautov00.dto.RegisterRequestDTO;
import com.group.sigautov00.entity.Cliente;
import com.group.sigautov00.entity.Genero;
import com.group.sigautov00.entity.Persona;
import com.group.sigautov00.entity.Usuario;
import com.group.sigautov00.repository.ClienteRepository;
import com.group.sigautov00.repository.PersonaRepository;
import com.group.sigautov00.repository.UsuarioRepository;
import com.group.sigautov00.util.JwtUtil;

import lombok.RequiredArgsConstructor;


@Service
@RequiredArgsConstructor
public class AuthService {
    private final UsuarioRepository usuarioRepository;
    private final PersonaRepository personaRepository;
    private final ClienteRepository clienteRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtService;
    
    public AuthResponseDTO register(RegisterRequestDTO request) {
        // checking...
        if (personaRepository.existsByEmail(request.getEmail())) {
            throw new IllegalArgumentException("Hay un usuario registrado con ese email");
        }
        if (personaRepository.existsByNumeroDocumento(request.getNumeroDocumento())) {
            throw new IllegalArgumentException("Hay un usuario registrado con ese nro de documento");
        }

        // Create Persona
        var persona = Persona.builder()
            .email(request.getEmail())
            .nombres(request.getNombres())
            .apellidoPaterno(request.getApellidoPaterno())
            .apellidoMaterno(request.getApellidoMaterno())
            .direccion(request.getDireccion())
            .fechaNacimiento(request.getFechaNacimiento())
            .sexo(Genero.valueOf(request.getSexo()))
            .numeroDocumento(request.getNumeroDocumento())
            .telefono(request.getTelefono())
            .createdAt(java.time.LocalDateTime.now())
            .updatedAt(java.time.LocalDateTime.now())
            .build();
        
        personaRepository.save(persona);
        
        // Create Usuario
        var usuario = Usuario.builder()
            .persona(persona)
            .passwordHash(passwordEncoder.encode(request.getPassword()))
            .build();
        
        usuarioRepository.save(usuario);

        // Create Cliente
        var cliente = Cliente.builder()
            .persona(persona)
            .estado(true)
            .createdAt(java.time.LocalDateTime.now())
            .updatedAt(java.time.LocalDateTime.now())
            .build();
        
        clienteRepository.save(cliente);

        var token = jwtService.generateToken(usuario);
        return AuthResponseDTO.builder()
            .token(token)
            .build();
    }
    
    public AuthResponseDTO login(LoginRequestDTO request) {
        var usuario = usuarioRepository.findByPersonaEmail(request.getEmail())
            .orElseThrow(() -> new UsernameNotFoundException("Email no encontrado"));
            
        if (!passwordEncoder.matches(request.getPassword(), usuario.getPasswordHash())) {
            throw new BadCredentialsException("Contrasena incorrecta");
        }
        
        var token = jwtService.generateToken(usuario);
        return AuthResponseDTO.builder()
            .token(token)
            .build();
    }
}
