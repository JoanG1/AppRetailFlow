package com.tuempresa.retailflow.service;

import com.tuempresa.retailflow.dto.CrearLocalDTO;
import com.tuempresa.retailflow.entity.Local;
import com.tuempresa.retailflow.repository.LocalRepository;
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
        List<Local> locales = List.of(new Local(1L, "Local 1", new ArrayList<>()));
        when(localRepository.findAll()).thenReturn(locales);

        List<Local> resultado = localService.obtenerTodasLosLocales();

        assertThat(resultado).hasSize(1);
        assertThat(resultado.get(0).getNombre()).isEqualTo("Local 1");
    }

    @Test
    void testObtenerLocalPorId() {
        List<Local> locales = List.of(new Local(1L, "Local 1", new ArrayList<>()));
        when(localRepository.findByid(1L)).thenReturn(locales);

        List<Local> resultado = localService.obtenerLocalPorId(1L);

        assertThat(resultado).isNotEmpty();
        assertThat(resultado.get(0).getNombre()).isEqualTo("Local 1");
    }

    @Test
    void testCrearLocal() {
        CrearLocalDTO dto = new CrearLocalDTO();
        dto.setNombre("Nuevo Local");

        Local savedLocal = new Local(1L, "Nuevo Local", new ArrayList<>());

        when(localRepository.save(any(Local.class))).thenReturn(savedLocal);

        Local resultado = localService.crearLocal(dto);

        assertThat(resultado.getNombre()).isEqualTo("Nuevo Local");
        verify(localRepository, times(1)).save(any(Local.class));
    }

    @Test
    void testEliminarLocal_Existe() {
        Local local = new Local(1L, "Eliminar Local", new ArrayList<>());
        when(localRepository.findById(1L)).thenReturn(Optional.of(local));

        localService.eliminarLocal(1L);

        verify(localRepository).deleteById(1L);
    }

    @Test
    void testEliminarLocal_NoExiste() {
        when(localRepository.findById(999L)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> localService.eliminarLocal(999L))
                .isInstanceOf(ResponseStatusException.class)
                .hasMessageContaining("local no encontrado");
    }

    @Test
    void testActualizarLocal_Existe() {
        Local existente = new Local(1L, "Nombre viejo", new ArrayList<>());
        CrearLocalDTO dto = new CrearLocalDTO();
        dto.setNombre("Nombre nuevo");

        when(localRepository.findById(1L)).thenReturn(Optional.of(existente));
        when(localRepository.save(any(Local.class))).thenReturn(existente);

        Local actualizado = localService.actualizarLocal(1L, dto);

        assertThat(actualizado.getNombre()).isEqualTo("Nombre nuevo");
    }

    @Test
    void testActualizarLocal_NoExiste() {
        CrearLocalDTO dto = new CrearLocalDTO();
        dto.setNombre("Nombre cualquiera");

        when(localRepository.findById(999L)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> localService.actualizarLocal(999L, dto))
                .isInstanceOf(RuntimeException.class)
                .hasMessageContaining("Bodega no encontrada");
    }

    @Test
    void testContarLocales() {
        when(localRepository.count()).thenReturn(5L);

        long total = localService.contarLocales();

        assertThat(total).isEqualTo(5L);
    }
}