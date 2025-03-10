package com.tuempresa.retailflow.service;

import com.tuempresa.retailflow.dto.LoginRequestDTO;
import com.tuempresa.retailflow.dto.RegisterRequestDTO;
import com.tuempresa.retailflow.entity.Usuario;
import com.tuempresa.retailflow.repository.UsuarioRepository;
import com.tuempresa.retailflow.config.JwtService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AuthService {

    private final UsuarioRepository usuarioRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;

    public AuthService(UsuarioRepository usuarioRepository, PasswordEncoder passwordEncoder, JwtService jwtService) {
        this.usuarioRepository = usuarioRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtService = jwtService;
    }

    public String login(LoginRequestDTO request) {
        Optional<Usuario> usuarioOpt = usuarioRepository.findByUsername(request.getUsername());

        if (usuarioOpt.isPresent() && passwordEncoder.matches(request.getPassword(), usuarioOpt.get().getPassword())) {
            return jwtService.generateToken(usuarioOpt.get().getUsername());
        }
        throw new RuntimeException("Credenciales inválidas");
    }

    public void register(RegisterRequestDTO request) {
        Usuario usuario = new Usuario();
        usuario.setUsername(request.getUsername());
        usuario.setPassword(passwordEncoder.encode(request.getPassword()));
        usuario.setRol(request.getRole());

        usuarioRepository.save(usuario);
    }
}
