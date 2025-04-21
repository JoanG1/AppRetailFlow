package com.tuempresa.retailflow.service;

import com.tuempresa.retailflow.Enum.Rol;
import com.tuempresa.retailflow.config.JwtService;
import com.tuempresa.retailflow.dto.LoginRequestDTO;
import com.tuempresa.retailflow.dto.RegisterRequestDTO;
import com.tuempresa.retailflow.entity.Usuario;
import com.tuempresa.retailflow.repository.UsuarioRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.server.ResponseStatusException;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class AuthServiceTest {

    @Mock
    private UsuarioRepository usuarioRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @Mock
    private JwtService jwtService;

    @InjectMocks
    private AuthService authService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void login_CredencialesCorrectas_devuelveToken() {
        // Arrange
        LoginRequestDTO loginRequest = new LoginRequestDTO();
        loginRequest.setUsername("usuario");
        loginRequest.setPassword("contrasena");

        Usuario usuario = new Usuario(1L, "usuario", "hashedPassword", Rol.CLIENTE);

        when(usuarioRepository.findByUsername("usuario")).thenReturn(Optional.of(usuario));
        when(passwordEncoder.matches("contrasena", "hashedPassword")).thenReturn(true);
        when(jwtService.generateToken("usuario")).thenReturn("jwt-token");

        // Act
        String result = authService.login(loginRequest);

        // Assert
        assertEquals("jwt-token", result);
    }

    @Test
    void login_CredencialesIncorrectas_lanzaExcepcion() {
        LoginRequestDTO loginRequest = new LoginRequestDTO();
        loginRequest.setUsername("usuario");
        loginRequest.setPassword("contrasena");

        Usuario usuario = new Usuario(1L, "usuario", "hashedPassword", Rol.CLIENTE);

        when(usuarioRepository.findByUsername("usuario")).thenReturn(Optional.of(usuario));
        when(passwordEncoder.matches("contrasena", "hashedPassword")).thenReturn(false);

        ResponseStatusException exception = assertThrows(ResponseStatusException.class,
                () -> authService.login(loginRequest));

        assertEquals(HttpStatus.UNAUTHORIZED, exception.getStatusCode());
    }

    @Test
    void register_UsuarioNuevo_registraCorrectamente() {
        RegisterRequestDTO registerRequest = new RegisterRequestDTO();
        registerRequest.setUsername("nuevoUsuario");
        registerRequest.setPassword("clave");

        when(usuarioRepository.findByUsername("nuevoUsuario")).thenReturn(Optional.empty());
        when(passwordEncoder.encode("clave")).thenReturn("claveCodificada");

        authService.register(registerRequest);

        ArgumentCaptor<Usuario> captor = ArgumentCaptor.forClass(Usuario.class);
        verify(usuarioRepository).save(captor.capture());

        Usuario usuarioGuardado = captor.getValue();
        assertEquals("nuevoUsuario", usuarioGuardado.getUsername());
        assertEquals("claveCodificada", usuarioGuardado.getPassword());
        assertEquals(Rol.CLIENTE, usuarioGuardado.getRol());
    }

    @Test
    void register_UsuarioExistente_lanzaExcepcion() {
        RegisterRequestDTO registerRequest = new RegisterRequestDTO();
        registerRequest.setUsername("usuarioExistente");
        registerRequest.setPassword("clave");

        when(usuarioRepository.findByUsername("usuarioExistente"))
                .thenReturn(Optional.of(new Usuario()));

        ResponseStatusException exception = assertThrows(ResponseStatusException.class,
                () -> authService.register(registerRequest));

        assertEquals(HttpStatus.BAD_REQUEST, exception.getStatusCode());
    }
}