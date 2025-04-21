package com.tuempresa.retailflow.service;

import com.tuempresa.retailflow.dto.SeccionDTO;
import com.tuempresa.retailflow.entity.Bodega;
import com.tuempresa.retailflow.entity.Seccion;
import com.tuempresa.retailflow.repository.BodegaRepository;
import com.tuempresa.retailflow.repository.SeccionRepository;
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
        List<Seccion> secciones = List.of(new Seccion());
        when(seccionRepository.findAll()).thenReturn(secciones);

        List<Seccion> resultado = seccionService.obtenerTodasLasSecciones();

        assertEquals(1, resultado.size());
        verify(seccionRepository, times(1)).findAll();
    }

    @Test
    void obtenerSeccionPorId_existente() {
        Seccion seccion = new Seccion();
        seccion.setId(1L);
        when(seccionRepository.findById(1L)).thenReturn(Optional.of(seccion));

        Seccion resultado = seccionService.obtenerSeccionPorId(1L);

        assertNotNull(resultado);
        assertEquals(1L, resultado.getId());
    }

    @Test
    void obtenerSeccionPorId_noExistente_lanzaExcepcion() {
        when(seccionRepository.findById(99L)).thenReturn(Optional.empty());

        RuntimeException ex = assertThrows(RuntimeException.class, () -> {
            seccionService.obtenerSeccionPorId(99L);
        });

        assertEquals("Sección no encontrada", ex.getMessage());
    }

    @Test
    void crearSeccion_exitosa() {
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
    }

    @Test
    void crearSeccion_faltaNombre_lanzaExcepcion() {
        SeccionDTO dto = new SeccionDTO();
        dto.setBodegaId(1L);
        dto.setNombre(" ");

        ResponseStatusException ex = assertThrows(ResponseStatusException.class, () -> {
            seccionService.crearSeccion(dto);
        });

        assertEquals(HttpStatus.BAD_REQUEST, ex.getStatusCode());
    }

    @Test
    void crearSeccion_duplicada_lanzaExcepcion() {
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
    }

    @Test
    void actualizarSeccion_exitosa() {
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
    }

    @Test
    void actualizarSeccion_duplicada_lanzaExcepcion() {
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
    }

    @Test
    void eliminarSeccion_correctamente() {
        doNothing().when(seccionRepository).deleteById(1L);

        assertDoesNotThrow(() -> seccionService.eliminarSeccion(1L));

        verify(seccionRepository, times(1)).deleteById(1L);
    }
}