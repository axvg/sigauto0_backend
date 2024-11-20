package com.group.sigautov00.service;

import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.group.sigautov00.dto.AuthResponseDTO;
import com.group.sigautov00.dto.LoginRequestDTO;
import com.group.sigautov00.dto.RegisterRequestDTO;
import com.group.sigautov00.entity.Genero;
import com.group.sigautov00.entity.Persona;
import com.group.sigautov00.entity.Usuario;
import com.group.sigautov00.repository.PersonaRepository;
import com.group.sigautov00.repository.UsuarioRepository;
import com.group.sigautov00.util.JwtUtil;

import lombok.RequiredArgsConstructor;


@Service
@RequiredArgsConstructor
public class AuthService {
private final UsuarioRepository usuarioRepository;
    private final PersonaRepository personaRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtService;
    
    public AuthResponseDTO register(RegisterRequestDTO request) {
        // Create Persona
        // console .log request
        System.out.println(request);

        // log the request in console




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
            .build();
        
        personaRepository.save(persona);
        
        // Create Usuario
        var usuario = Usuario.builder()
            .persona(persona)
            .passwordHash(passwordEncoder.encode(request.getPassword()))
            .build();
        
        usuarioRepository.save(usuario);
        
        var token = jwtService.generateToken(usuario);
        return AuthResponseDTO.builder()
            .token(token)
            .build();
    }
    
    public AuthResponseDTO login(LoginRequestDTO request) {
        var usuario = usuarioRepository.findByPersonaEmail(request.getEmail())
            .orElseThrow(() -> new UsernameNotFoundException("User not found"));
            
        if (!passwordEncoder.matches(request.getPassword(), usuario.getPasswordHash())) {
            throw new BadCredentialsException("Invalid password");
        }
        
        var token = jwtService.generateToken(usuario);
        return AuthResponseDTO.builder()
            .token(token)
            .build();
    }
}
