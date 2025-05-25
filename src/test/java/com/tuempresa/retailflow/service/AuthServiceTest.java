    package com.tuempresa.retailflow.service;

    import com.tuempresa.retailflow.Enum.Rol;
    import com.tuempresa.retailflow.config.JwtService;
    import com.tuempresa.retailflow.dto.LoginRequestDTO;
    import com.tuempresa.retailflow.dto.RegisterRequestDTO;
    import com.tuempresa.retailflow.dto.TokenDTO;
    import com.tuempresa.retailflow.entity.Usuario;
    import com.tuempresa.retailflow.repository.UsuarioRepository;
    import com.tuempresa.retailflow.testRail.TestRailClient;
    import com.tuempresa.retailflow.testRail.TestRailReporter;
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
            boolean passed = false;

            try {
                // Arrange
                LoginRequestDTO loginRequest = new LoginRequestDTO();
                loginRequest.setUsername("usuario");
                loginRequest.setPassword("contrasena");

                Usuario usuario = new Usuario(1L, "usuario", "hashedPassword", Rol.CLIENTE);

                when(usuarioRepository.findByUsername("usuario")).thenReturn(Optional.of(usuario));
                when(passwordEncoder.matches("contrasena", "hashedPassword")).thenReturn(true);
                when(jwtService.generateToken("usuario")).thenReturn("jwt-token");

                // Act
                 TokenDTO result = authService.login(loginRequest);
                 String token = result.getToken();
                // Assert
                assertEquals("jwt-token", token);
                passed = true;
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                try {
                    TestRailClient client = new TestRailClient(
                            "https://codigoabiertop.testrail.io",
                            "codigo.abierto.p@gmail.com",
                            "pknkko2Hs9S8IPUANOzE-KVHY/dyV3IhGvtilMXUV"
                    );
                    TestRailReporter reporter = new TestRailReporter(client, 3, 5, 36);
                    reporter.reportResultPerTest(
                            "Login con credenciales válidas devuelve token",
                            passed,
                            "Automatizado - Login correcto con token JWT"
                    );
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }

        @Test
        void login_CredencialesIncorrectas_lanzaExcepcion() {
            boolean passed = false;

            try {
                // Arrange
                LoginRequestDTO loginRequest = new LoginRequestDTO();
                loginRequest.setUsername("usuario");
                loginRequest.setPassword("Stejerosam#77");

                Usuario usuario = new Usuario(1L, "usuario", "hashedPassword", Rol.CLIENTE);

                when(usuarioRepository.findByUsername("usuario")).thenReturn(Optional.of(usuario));
                when(passwordEncoder.matches("Stejerosam#77", "hashedPassword")).thenReturn(false);

                // Act & Assert
                ResponseStatusException exception = assertThrows(ResponseStatusException.class,
                        () -> authService.login(loginRequest));

                assertEquals(HttpStatus.UNAUTHORIZED, exception.getStatusCode());
                passed = true;

            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                try {
                    TestRailClient client = new TestRailClient(
                            "https://codigoabiertop.testrail.io",
                            "codigo.abierto.p@gmail.com",
                            "pknkko2Hs9S8IPUANOzE-KVHY/dyV3IhGvtilMXUV"
                    );
                    TestRailReporter reporter = new TestRailReporter(client, 3, 5, 36);
                    reporter.reportResultPerTest(
                            "Login con credenciales incorrectas lanza excepción",
                            passed,
                            "Automatizado - Login inválido con error HTTP 401"
                    );
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }

        @Test
        void register_UsuarioNuevo_registraCorrectamente() {
            boolean passed = false;

            try {
                // Arrange
                RegisterRequestDTO registerRequest = new RegisterRequestDTO();
                registerRequest.setUsername("nuevoUsuario");
                registerRequest.setPassword("Stejerosam#77");

                when(usuarioRepository.findByUsername("nuevoUsuario")).thenReturn(Optional.empty());
                when(passwordEncoder.encode("Stejerosam#77")).thenReturn("claveCodificada");

                // Act
                authService.register(registerRequest);

                // Assert
                ArgumentCaptor<Usuario> captor = ArgumentCaptor.forClass(Usuario.class);
                verify(usuarioRepository).save(captor.capture());

                Usuario usuarioGuardado = captor.getValue();
                assertEquals("nuevoUsuario", usuarioGuardado.getUsername());
                assertEquals("claveCodificada", usuarioGuardado.getPassword());
                assertEquals(Rol.CLIENTE, usuarioGuardado.getRol());

                passed = true;

            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                try {
                    TestRailClient client = new TestRailClient(
                            "https://codigoabiertop.testrail.io",
                            "codigo.abierto.p@gmail.com",
                            "pknkko2Hs9S8IPUANOzE-KVHY/dyV3IhGvtilMXUV"
                    );
                    TestRailReporter reporter = new TestRailReporter(client, 3, 5, 36);
                    reporter.reportResultPerTest(
                            "Registro de usuario nuevo exitoso",
                            passed,
                            "Automatizado - Validación de guardado de nuevo cliente"
                    );
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }

        @Test
        void register_UsuarioExistente_lanzaExcepcion() {
            boolean passed = false;

            try {
                // Arrange
                RegisterRequestDTO registerRequest = new RegisterRequestDTO();
                registerRequest.setUsername("usuarioExistente");
                registerRequest.setPassword("Stejerosam#77");

                when(usuarioRepository.findByUsername("usuarioExistente"))
                        .thenReturn(Optional.of(new Usuario()));

                // Act & Assert
                ResponseStatusException exception = assertThrows(ResponseStatusException.class,
                        () -> authService.register(registerRequest));

                assertEquals(HttpStatus.BAD_REQUEST, exception.getStatusCode());
                passed = true;

            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                try {
                    TestRailClient client = new TestRailClient(
                            "https://codigoabiertop.testrail.io",
                            "codigo.abierto.p@gmail.com",
                            "pknkko2Hs9S8IPUANOzE-KVHY/dyV3IhGvtilMXUV"
                    );
                    TestRailReporter reporter = new TestRailReporter(client, 3, 5, 36);
                    reporter.reportResultPerTest(
                            "Registro con usuario existente lanza excepción",
                            passed,
                            "Automatizado - Registro fallido con usuario duplicado"
                    );
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }