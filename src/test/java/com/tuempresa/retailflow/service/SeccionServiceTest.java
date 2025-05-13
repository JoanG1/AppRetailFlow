package com.tuempresa.retailflow.service;

import com.tuempresa.retailflow.dto.SeccionDTO;
import com.tuempresa.retailflow.entity.Bodega;
import com.tuempresa.retailflow.entity.Producto;
import com.tuempresa.retailflow.entity.Seccion;
import com.tuempresa.retailflow.repository.BodegaRepository;
import com.tuempresa.retailflow.repository.SeccionRepository;
import com.tuempresa.retailflow.testRail.TestRailClient;
import com.tuempresa.retailflow.testRail.TestRailReporter;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class SeccionServiceTest {

    private SeccionRepository seccionRepository;
    private BodegaRepository bodegaRepository;
    private SeccionService seccionService;

    @BeforeEach
    void setUp() {
        seccionRepository = mock(SeccionRepository.class);
        bodegaRepository = mock(BodegaRepository.class);
        seccionService = new SeccionService(seccionRepository, bodegaRepository);
    }

    @Test
    void obtenerTodasLasSecciones_retornaListaCorrecta() {

        boolean passed = false;
        try{

            List<Seccion> secciones = List.of(new Seccion());
            when(seccionRepository.findAll()).thenReturn(secciones);

            List<Seccion> resultado = seccionService.obtenerTodasLasSecciones();

            assertEquals(1, resultado.size());
            verify(seccionRepository, times(1)).findAll();

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
                        "Obtener todas las secciones",
                        passed,
                        "Automatizado - Verifica que se obtengan todas las secciones"
                );

            }catch (Exception e){
                e.printStackTrace();
            }
        }

    }

    @Test
    void obtenerSeccionPorId_existente() {
        boolean passed = false;
        try{

            Seccion seccion = new Seccion();
            seccion.setId(1L);
            when(seccionRepository.findById(1L)).thenReturn(Optional.of(seccion));

            Seccion resultado = seccionService.obtenerSeccionPorId(1L);

            assertNotNull(resultado);
            assertEquals(1L, resultado.getId());

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
                        "Obtener secccion por ID",
                        passed,
                        "Automatizado - Verifica que se obtenga una seccion por ID"
                );

            }catch (Exception e){
                e.printStackTrace();
            }
        }

    }

    @Test
    void obtenerSeccionPorId_noExistente_lanzaExcepcion() {

        boolean passed = false;
        try{

            when(seccionRepository.findById(99L)).thenReturn(Optional.empty());

            RuntimeException ex = assertThrows(RuntimeException.class, () -> {
                seccionService.obtenerSeccionPorId(99L);
            });

            assertEquals("Sección no encontrada", ex.getMessage());

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
                        "Obtener secciones por ID inexistente",
                        passed,
                        "Automatizado - Verifica no se obtenga ninguna seccion inexistente"
                );

            }catch (Exception e){
                e.printStackTrace();
            }
        }

    }

    @Test
    void crearSeccion_exitosa() {
        boolean passed = false;
        try{

            Bodega bodega = new Bodega();
            bodega.setId(1L);

            SeccionDTO dto = new SeccionDTO();
            dto.setNombre("Zona A");
            dto.setBodegaId(1L);

            when(bodegaRepository.findById(1L)).thenReturn(Optional.of(bodega));
            when(seccionRepository.existsByNombreAndBodega("Zona A", bodega)).thenReturn(false);
            when(seccionRepository.save(any(Seccion.class))).thenAnswer(i -> i.getArguments()[0]);

            Seccion resultado = seccionService.crearSeccion(dto);

            assertEquals("Zona A", resultado.getNombre());
            assertEquals(bodega, resultado.getBodega());

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
                        "Creacion de seccion",
                        passed,
                        "Automatizado - Verifica que se cree correctamente una seccion"
                );

            }catch (Exception e){
                e.printStackTrace();
            }
        }

    }

    @Test
    void crearSeccion_faltaNombre_lanzaExcepcion() {
        boolean passed = false;
        try{

            SeccionDTO dto = new SeccionDTO();
            dto.setBodegaId(1L);
            dto.setNombre(" ");

            ResponseStatusException ex = assertThrows(ResponseStatusException.class, () -> {
                seccionService.crearSeccion(dto);
            });

            assertEquals(HttpStatus.BAD_REQUEST, ex.getStatusCode());

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
                        "Creacion de seccion sin nombre",
                        passed,
                        "Automatizado - Verifica que no se permita crear una seccion sin nombre"
                );

            }catch (Exception e){
                e.printStackTrace();
            }
        }

    }

    @Test
    void crearSeccion_duplicada_lanzaExcepcion() {

        boolean passed = false;
        try{

            SeccionDTO dto = new SeccionDTO();
            dto.setNombre("Zona A");
            dto.setBodegaId(1L);

            Bodega bodega = new Bodega();
            bodega.setId(1L);

            when(bodegaRepository.findById(1L)).thenReturn(Optional.of(bodega));
            when(seccionRepository.existsByNombreAndBodega("Zona A", bodega)).thenReturn(true);

            ResponseStatusException ex = assertThrows(ResponseStatusException.class, () -> {
                seccionService.crearSeccion(dto);
            });

            assertEquals(HttpStatus.CONFLICT, ex.getStatusCode());

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
                        "Creacion de seccion duplicada",
                        passed,
                        "Automatizado - Verifica que no se cree una seccion que este duplicada"
                );

            }catch (Exception e){
                e.printStackTrace();
            }
        }

    }

    @Test
    void actualizarSeccion_exitosa() {

        boolean passed = false;
        try{

            Seccion seccion = new Seccion();
            seccion.setId(1L);
            seccion.setNombre("Vieja");

            Bodega bodega = new Bodega();
            bodega.setId(1L);

            SeccionDTO dto = new SeccionDTO();
            dto.setNombre("Nueva");
            dto.setBodegaId(1L);

            when(seccionRepository.findById(1L)).thenReturn(Optional.of(seccion));
            when(bodegaRepository.findById(1L)).thenReturn(Optional.of(bodega));
            when(seccionRepository.existsByNombreAndBodega("Nueva", bodega)).thenReturn(false);
            when(seccionRepository.save(any())).thenAnswer(i -> i.getArguments()[0]);

            Seccion actualizada = seccionService.actualizarSeccion(1L, dto);

            assertEquals("Nueva", actualizada.getNombre());

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
                        "Actualizar seccion",
                        passed,
                        "Automatizado - Verifica que se actualice correctamente una seccion"
                );

            }catch (Exception e){
                e.printStackTrace();
            }
        }

    }

    @Test
    void actualizarSeccion_duplicada_lanzaExcepcion() {

        boolean passed = false;
        try{

            Seccion seccion = new Seccion();
            seccion.setId(1L);
            seccion.setNombre("Otra");

            Bodega bodega = new Bodega();
            bodega.setId(1L);

            SeccionDTO dto = new SeccionDTO();
            dto.setNombre("Duplicada");
            dto.setBodegaId(1L);

            when(seccionRepository.findById(1L)).thenReturn(Optional.of(seccion));
            when(bodegaRepository.findById(1L)).thenReturn(Optional.of(bodega));
            when(seccionRepository.existsByNombreAndBodega("Duplicada", bodega)).thenReturn(true);

            Exception ex = assertThrows(IllegalStateException.class, () -> {
                seccionService.actualizarSeccion(1L, dto);
            });

            assertEquals("Ya existe una sección con este nombre en la bodega.", ex.getMessage());

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
                        "Actualizar seccion duplicada",
                        passed,
                        "Automatizado - Verifica que no se actualice una seccion duplicada"
                );

            }catch (Exception e){
                e.printStackTrace();
            }
        }

    }

    @Test
    void eliminarSeccion_correctamente() {

        boolean passed = false;
        try{

            doNothing().when(seccionRepository).deleteById(1L);

            assertDoesNotThrow(() -> seccionService.eliminarSeccion(1L));

            verify(seccionRepository, times(1)).deleteById(1L);

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
                        "Eliminar seccion",
                        passed,
                        "Automatizado - Verifica que se elimina una seccion correctamente"
                );

            }catch (Exception e){
                e.printStackTrace();
            }
        }

    }
}