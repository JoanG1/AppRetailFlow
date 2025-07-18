package com.tuempresa.retailflow.service;

import com.tuempresa.retailflow.Enum.Rol;
import com.tuempresa.retailflow.dto.LoginRequestDTO;
import com.tuempresa.retailflow.dto.RegisterRequestDTO;
import com.tuempresa.retailflow.dto.TokenDTO;
import com.tuempresa.retailflow.entity.Usuario;
import com.tuempresa.retailflow.repository.UsuarioRepository;
import com.tuempresa.retailflow.config.JwtService;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

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

    public TokenDTO login(LoginRequestDTO request) {
        Optional<Usuario> usuarioOpt = usuarioRepository.findByUsername(request.getUsername());

        if (usuarioOpt.isPresent() && passwordEncoder.matches(request.getPassword(), usuarioOpt.get().getPassword())) {
            TokenDTO LoginInfo = new TokenDTO();
            LoginInfo.setToken(jwtService.generateToken(usuarioOpt.get().getUsername()));
            LoginInfo.setUsuarioId(usuarioOpt.get().getId());

            return LoginInfo;
        }
        throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Credenciales inválidas"); //Envia un codigo 401 UNAUTHORIZED
    }

    public void register(RegisterRequestDTO request) {
        // Validación de contraseña
        if (!isPasswordValid(request.getPassword())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
                    "La contraseña debe tener al menos 8 caracteres, una letra mayúscula y un carácter especial");
        }

        if (usuarioRepository.findByUsername(request.getUsername()).isPresent()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "El usuario ya existe");
        }

        Usuario usuario = new Usuario();
        usuario.setUsername(request.getUsername());
        usuario.setPassword(passwordEncoder.encode(request.getPassword()));
        usuario.setRol(Rol.CLIENTE);

        usuarioRepository.save(usuario);
    }

    // ✅ Método privado para validar la contraseña
    private boolean isPasswordValid(String password) {
        if (password == null || password.length() < 8) {
            return false;
        }
        boolean hasUppercase = password.chars().anyMatch(Character::isUpperCase);
        boolean hasSpecialChar = password.matches(".*[^a-zA-Z0-9].*");

        return hasUppercase && hasSpecialChar;
    }
}
