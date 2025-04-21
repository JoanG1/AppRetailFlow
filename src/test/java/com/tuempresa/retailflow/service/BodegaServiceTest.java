package com.tuempresa.retailflow.service;

import com.tuempresa.retailflow.dto.BodegaDTO;
import com.tuempresa.retailflow.dto.CrearBodegaDTO;
import com.tuempresa.retailflow.entity.Bodega;
import com.tuempresa.retailflow.entity.ProductoBodega;
import com.tuempresa.retailflow.entity.Seccion;
import com.tuempresa.retailflow.repository.BodegaRepository;
import com.tuempresa.retailflow.repository.SeccionRepository;
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
        List<Bodega> bodegasMock = List.of(new Bodega(), new Bodega());
        when(bodegaRepository.findAll()).thenReturn(bodegasMock);

        List<Bodega> resultado = bodegaService.obtenerTodasLasBodegas();

        assertEquals(2, resultado.size());
        verify(bodegaRepository).findAll();
    }

    /**
     * Test para obtener una bodega por ID cuando existe.
     */
    @Test
    void obtenerBodegaPorId_deberiaRetornarBodegaSiExiste() {
        Bodega bodega = new Bodega();
        bodega.setId(1L);
        when(bodegaRepository.findById(1L)).thenReturn(Optional.of(bodega));

        Bodega resultado = bodegaService.obtenerBodegaPorId(1L);

        assertEquals(1L, resultado.getId());
    }

    /**
     * Test para obtener una bodega por ID cuando NO existe.
     */
    @Test
    void obtenerBodegaPorId_deberiaLanzarExcepcionSiNoExiste() {
        when(bodegaRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(RuntimeException.class, () -> bodegaService.obtenerBodegaPorId(1L));
    }

    /**
     * Test para crear una nueva bodega con secciones.
     */
    @Test
    void crearBodega_deberiaCrearBodegaConSecciones() {
        CrearBodegaDTO dto = new CrearBodegaDTO();
        dto.setNombre("Bodega Central");
        dto.setSecciones(List.of("Frutas", "Verduras"));

        when(bodegaRepository.save(any())).thenAnswer(invocation -> invocation.getArgument(0));

        Bodega resultado = bodegaService.crearBodega(dto);

        assertEquals("Bodega Central", resultado.getNombre());
        assertEquals(2, resultado.getSecciones().size());
        assertEquals("Frutas", resultado.getSecciones().get(0).getNombre());
    }

    /**
     * Test para crear una bodega vacía y automáticamente agregarle una sección por defecto.
     */
    @Test
    void crearBodegaVacia_deberiaCrearBodegaConSeccionPorDefecto() {
        BodegaDTO dto = new BodegaDTO();
        dto.setNombre("Nueva Bodega");

        Bodega bodegaGuardada = new Bodega();
        bodegaGuardada.setId(1L);
        bodegaGuardada.setNombre("Nueva Bodega");

        when(bodegaRepository.save(any())).thenReturn(bodegaGuardada);
        when(bodegaRepository.getBodegaById(1L)).thenReturn(Optional.of(bodegaGuardada));

        Bodega resultado = bodegaService.crearBodegaVacia(dto);

        assertEquals("Nueva Bodega", resultado.getNombre());
        verify(seccionRepository).save(any(Seccion.class));
    }

    /**
     * Test para actualizar una bodega existente.
     */
    @Test
    void actualizarBodega_deberiaActualizarNombre() {
        Bodega existente = new Bodega();
        existente.setId(1L);
        existente.setNombre("Viejo Nombre");

        BodegaDTO dto = new BodegaDTO();
        dto.setNombre("Nuevo Nombre");

        when(bodegaRepository.findById(1L)).thenReturn(Optional.of(existente));
        when(bodegaRepository.save(any())).thenAnswer(invocation -> invocation.getArgument(0));

        Bodega actualizado = bodegaService.actualizarBodega(1L, dto);

        assertEquals("Nuevo Nombre", actualizado.getNombre());
    }

    /**
     * Test para eliminar una bodega sin productos en sus secciones.
     */
    @Test
    void eliminarBodega_deberiaEliminarSiNoHayProductos() {
        Bodega bodega = new Bodega();
        bodega.setId(1L);

        Seccion seccion = new Seccion();
        seccion.setProductosEnSeccion(Collections.emptyList());

        when(bodegaRepository.findById(1L)).thenReturn(Optional.of(bodega));
        when(seccionRepository.findByBodegaId(1L)).thenReturn(List.of(seccion));

        bodegaService.eliminarBodega(1L);

        verify(seccionRepository).deleteAll(any());
        verify(bodegaRepository).deleteById(1L);
    }

    /**
     * Test para NO eliminar una bodega si tiene productos en sus secciones.
     */
    @Test
    void eliminarBodega_deberiaLanzarExcepcionSiTieneProductos() {
        Bodega bodega = new Bodega();
        bodega.setId(1L);

        Seccion seccionConProductos = new Seccion();
        seccionConProductos.setProductosEnSeccion(List.of(new ProductoBodega())); // simula productos

        when(bodegaRepository.findById(1L)).thenReturn(Optional.of(bodega));
        when(seccionRepository.findByBodegaId(1L)).thenReturn(List.of(seccionConProductos));

        assertThrows(RuntimeException.class, () -> bodegaService.eliminarBodega(1L));
        verify(bodegaRepository, never()).deleteById(any());
    }

}