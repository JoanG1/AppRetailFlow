package com.tuempresa.retailflow.service;

import com.tuempresa.retailflow.dto.BodegaDTO;
import com.tuempresa.retailflow.dto.CrearBodegaDTO;
import com.tuempresa.retailflow.entity.Bodega;
import com.tuempresa.retailflow.entity.ProductoBodega;
import com.tuempresa.retailflow.entity.Seccion;
import com.tuempresa.retailflow.repository.BodegaRepository;
import com.tuempresa.retailflow.repository.SeccionRepository;
import com.tuempresa.retailflow.testRail.TestRailClient;
import com.tuempresa.retailflow.testRail.TestRailReporter;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class BodegaServiceTest {

    private BodegaRepository bodegaRepository;
    private SeccionRepository seccionRepository;
    private BodegaService bodegaService;

    @BeforeEach
    void setUp() {
        bodegaRepository = mock(BodegaRepository.class);
        seccionRepository = mock(SeccionRepository.class);
        bodegaService = new BodegaService(bodegaRepository, seccionRepository);
    }

    /**
     * Test para verificar que obtenerTodasLasBodegas() retorna todas las bodegas.
     */
    @Test
    void obtenerTodasLasBodegas_deberiaRetornarTodasLasBodegas() {
        boolean passed = false;

        try {
            // Arrange
            List<Bodega> bodegasMock = List.of(new Bodega(), new Bodega());
            when(bodegaRepository.findAll()).thenReturn(bodegasMock);

            // Act
            List<Bodega> resultado = bodegaService.obtenerTodasLasBodegas();

            // Assert
            assertEquals(2, resultado.size());
            verify(bodegaRepository).findAll();

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
                        "Obtener todas las bodegas devuelve la lista completa",
                        passed,
                        "Automatizado - Verificación de obtención total desde repositorio"
                );
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * Test para obtener una bodega por ID cuando existe.
     */
    @Test
    void obtenerBodegaPorId_deberiaRetornarBodegaSiExiste() {
        boolean passed = false;

        try {
            // Arrange
            Bodega bodega = new Bodega();
            bodega.setId(1L);
            when(bodegaRepository.findById(1L)).thenReturn(Optional.of(bodega));

            // Act
            Bodega resultado = bodegaService.obtenerBodegaPorId(1L);

            // Assert
            assertEquals(1L, resultado.getId());
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
                        "Obtener bodega por ID existente devuelve bodega",
                        passed,
                        "Automatizado - Bodega encontrada correctamente por ID"
                );
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * Test para obtener una bodega por ID cuando NO existe.
     */
    @Test
    void obtenerBodegaPorId_deberiaLanzarExcepcionSiNoExiste() {
        boolean passed = false;

        try {
            // Arrange
            when(bodegaRepository.findById(1L)).thenReturn(Optional.empty());

            // Act & Assert
            assertThrows(RuntimeException.class, () -> bodegaService.obtenerBodegaPorId(1L));

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
                        "Obtener bodega inexistente lanza excepción",
                        passed,
                        "Automatizado - Lanzamiento de excepción al no encontrar bodega"
                );
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * Test para crear una nueva bodega con secciones.
     */
    @Test
    void crearBodega_deberiaCrearBodegaConSecciones() {
        boolean passed = false;

        try {
            // Arrange
            CrearBodegaDTO dto = new CrearBodegaDTO();
            dto.setNombre("Bodega Central");
            dto.setSecciones(List.of("Frutas", "Verduras"));

            when(bodegaRepository.save(any())).thenAnswer(invocation -> invocation.getArgument(0));

            // Act
            Bodega resultado = bodegaService.crearBodega(dto);

            // Assert
            assertEquals("Bodega Central", resultado.getNombre());
            assertEquals(2, resultado.getSecciones().size());
            assertEquals("Frutas", resultado.getSecciones().get(0).getNombre());

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
                        "Crear bodega con secciones correctamente",
                        passed,
                        "Automatizado - Registro de bodega y creación de secciones"
                );
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * Test para crear una bodega vacía y automáticamente agregarle una sección por defecto.
     */
    @Test
    void crearBodegaVacia_deberiaCrearBodegaConSeccionPorDefecto() {
        boolean passed = false;

        try {
            // Arrange
            BodegaDTO dto = new BodegaDTO();
            dto.setNombre("Nueva Bodega");

            Bodega bodegaGuardada = new Bodega();
            bodegaGuardada.setId(1L);
            bodegaGuardada.setNombre("Nueva Bodega");

            when(bodegaRepository.save(any())).thenReturn(bodegaGuardada);
            when(bodegaRepository.getBodegaById(1L)).thenReturn(Optional.of(bodegaGuardada));

            // Act
            Bodega resultado = bodegaService.crearBodegaVacia(dto);

            // Assert
            assertEquals("Nueva Bodega", resultado.getNombre());
            verify(seccionRepository).save(any(Seccion.class));

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
                        "Crear bodega vacía con sección por defecto",
                        passed,
                        "Automatizado - Verificación de creación con lógica por defecto"
                );
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * Test para actualizar una bodega existente.
     */
    @Test
    void actualizarBodega_deberiaActualizarNombre() {
        boolean passed = false;

        try {
            // Arrange
            Bodega existente = new Bodega();
            existente.setId(1L);
            existente.setNombre("Viejo Nombre");

            BodegaDTO dto = new BodegaDTO();
            dto.setNombre("Nuevo Nombre");

            when(bodegaRepository.findById(1L)).thenReturn(Optional.of(existente));
            when(bodegaRepository.save(any())).thenAnswer(invocation -> invocation.getArgument(0));

            // Act
            Bodega actualizado = bodegaService.actualizarBodega(1L, dto);

            // Assert
            assertEquals("Nuevo Nombre", actualizado.getNombre());
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
                        "Actualizar nombre de bodega correctamente",
                        passed,
                        "Automatizado - Modificación de nombre en entidad Bodega"
                );
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * Test para eliminar una bodega sin productos en sus secciones.
     */
    @Test
    void eliminarBodega_deberiaEliminarSiNoHayProductos() {
        boolean passed = false;

        try {
            Bodega bodega = new Bodega();
            bodega.setId(1L);

            Seccion seccion = new Seccion();
            seccion.setProductosEnSeccion(Collections.emptyList());

            when(bodegaRepository.findById(1L)).thenReturn(Optional.of(bodega));
            when(seccionRepository.findByBodegaId(1L)).thenReturn(List.of(seccion));

            // Act
            bodegaService.eliminarBodega(1L);

            // Assert
            verify(seccionRepository).deleteAll(any());
            verify(bodegaRepository).deleteById(1L);

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
                        "Eliminar bodega sin productos asociados",
                        passed,
                        "Automatizado - Eliminación exitosa de bodega sin dependencias"
                );
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * Test para NO eliminar una bodega si tiene productos en sus secciones.
     */
    @Test
    void eliminarBodega_deberiaLanzarExcepcionSiTieneProductos() {

        boolean passed = false;
        try{

            Bodega bodega = new Bodega();
            bodega.setId(1L);

            Seccion seccionConProductos = new Seccion();
            seccionConProductos.setProductosEnSeccion(List.of(new ProductoBodega())); // simula productos

            when(bodegaRepository.findById(1L)).thenReturn(Optional.of(bodega));
            when(seccionRepository.findByBodegaId(1L)).thenReturn(List.of(seccionConProductos));

            assertThrows(RuntimeException.class, () -> bodegaService.eliminarBodega(1L));
            verify(bodegaRepository, never()).deleteById(any());

            passed = true;

        }catch (Exception e){
            e.printStackTrace();
        }finally {
            try{

                TestRailClient client = new TestRailClient(
                        "https://codigoabiertop.testrail.io",
                        "codigo.abierto.p@gmail.com",
                        "pknkko2Hs9S8IPUANOzE-KVHY/dyV3IhGvtilMXUV"
                );
                TestRailReporter reporter = new TestRailReporter(client, 3, 5, 36);
                reporter.reportResultPerTest(
                        "Eliminar bodega con productos asociados",
                        passed,
                        "Automatizado - Mensaje de excepcion sobre eliminacion de bodega"
                );

            }catch (Exception e){
                e.printStackTrace();
            }

        }

    }

}