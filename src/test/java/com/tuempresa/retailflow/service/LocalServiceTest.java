package com.tuempresa.retailflow.service;

import com.tuempresa.retailflow.dto.CrearLocalDTO;
import com.tuempresa.retailflow.entity.Local;
import com.tuempresa.retailflow.repository.LocalRepository;
import com.tuempresa.retailflow.testRail.TestRailClient;
import com.tuempresa.retailflow.testRail.TestRailReporter;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.*;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.*;

class LocalServiceTest {

    @Mock
    private LocalRepository localRepository;

    @InjectMocks
    private LocalService localService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testObtenerTodasLosLocales() {
        boolean passed = false;

        try {
            List<Local> locales = List.of(new Local(1L, "Local 1", new ArrayList<>()));
            when(localRepository.findAll()).thenReturn(locales);

            List<Local> resultado = localService.obtenerTodasLosLocales();

            assertThat(resultado).hasSize(1);
            assertThat(resultado.get(0).getNombre()).isEqualTo("Local 1");

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
                // Reemplaza los valores 3 (projectId), 5 (suiteId), 36 (runId) por los adecuados si cambian
                TestRailReporter reporter = new TestRailReporter(client, 3, 5, 36);
                reporter.reportResultPerTest(
                        "Obtener todos los locales",
                        passed,
                        "Automatizado - Verifica que se devuelva la lista completa de locales"
                );
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }


    @Test
    void testObtenerLocalPorId() {
        boolean passed = false;

        try {
            List<Local> locales = List.of(new Local(1L, "Local 1", new ArrayList<>()));
            when(localRepository.findByid(1L)).thenReturn(locales);

            List<Local> resultado = localService.obtenerLocalPorId(1L);

            assertThat(resultado).isNotEmpty();
            assertThat(resultado.get(0).getNombre()).isEqualTo("Local 1");

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
                        "Obtener local por ID",
                        passed,
                        "Automatizado - Verifica que se obtenga correctamente el local por ID"
                );
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }


    @Test
    void testCrearLocal() {
        boolean passed = false;

        try {
            CrearLocalDTO dto = new CrearLocalDTO();
            dto.setNombre("Nuevo Local");

            Local savedLocal = new Local(1L, "Nuevo Local", new ArrayList<>());

            when(localRepository.save(any(Local.class))).thenReturn(savedLocal);

            Local resultado = localService.crearLocal(dto);

            assertThat(resultado.getNombre()).isEqualTo("Nuevo Local");
            verify(localRepository, times(1)).save(any(Local.class));

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
                        "Crear nuevo local",
                        passed,
                        "Automatizado - Verifica que se cree un local correctamente usando DTO"
                );
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }


    @Test
    void testEliminarLocal_Existe() {
        boolean passed = false;

        try {
            Local local = new Local(1L, "Eliminar Local", new ArrayList<>());
            when(localRepository.findById(1L)).thenReturn(Optional.of(local));

            localService.eliminarLocal(1L);

            verify(localRepository).deleteById(1L);

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
                        "Eliminar local existente",
                        passed,
                        "Automatizado - Verifica que se elimine un local cuando existe"
                );
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }


    @Test
    void testEliminarLocal_NoExiste() {
        boolean passed = false;

        try {
            when(localRepository.findById(999L)).thenReturn(Optional.empty());

            assertThatThrownBy(() -> localService.eliminarLocal(999L))
                    .isInstanceOf(ResponseStatusException.class)
                    .hasMessageContaining("local no encontrado");

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
                        "Eliminar local inexistente",
                        passed,
                        "Automatizado - Verifica que se lance excepción si el local no existe"
                );
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }


    @Test
    void testActualizarLocal_Existe() {
        boolean passed = false;

        try {
            Local existente = new Local(1L, "Nombre viejo", new ArrayList<>());
            CrearLocalDTO dto = new CrearLocalDTO();
            dto.setNombre("Nombre nuevo");

            when(localRepository.findById(1L)).thenReturn(Optional.of(existente));
            when(localRepository.save(any(Local.class))).thenReturn(existente);

            Local actualizado = localService.actualizarLocal(1L, dto);

            assertThat(actualizado.getNombre()).isEqualTo("Nombre nuevo");

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
                        "Actualizar local existente",
                        passed,
                        "Automatizado - Verifica que se actualice correctamente el nombre de un local existente"
                );
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }


    @Test
    void testActualizarLocal_NoExiste() {
        boolean passed = false;

        try {
            CrearLocalDTO dto = new CrearLocalDTO();
            dto.setNombre("Nombre cualquiera");

            when(localRepository.findById(999L)).thenReturn(Optional.empty());

            assertThatThrownBy(() -> localService.actualizarLocal(999L, dto))
                    .isInstanceOf(RuntimeException.class)
                    .hasMessageContaining("Bodega no encontrada");

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
                        "Actualizar local inexistente",
                        passed,
                        "Automatizado - Verifica que se lance excepción si el local a actualizar no existe"
                );
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }


    @Test
    void testContarLocales() {
        boolean passed = false;

        try {
            when(localRepository.count()).thenReturn(5L);

            long total = localService.contarLocales();

            assertThat(total).isEqualTo(5L);

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
                        "Contar locales",
                        passed,
                        "Automatizado - Verifica que se cuente correctamente el total de locales"
                );
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

}