package com.tuempresa.retailflow.service;

import com.tuempresa.retailflow.dto.ProductoDTO;
import com.tuempresa.retailflow.entity.Producto;
import com.tuempresa.retailflow.repository.ProductoRepository;
import com.tuempresa.retailflow.repository.ProductoBodegaRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class ProductoServiceTest {

    @Mock
    private ProductoRepository productoRepository;

    @Mock
    private ProductoBodegaRepository productoBodegaRepository;

    @InjectMocks
    private ProductoService productoService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    /**
     * ✅ Test para obtener todos los productos
     */
    @Test
    void obtenerProductos_devuelveListaDeProductos() {
        List<Producto> mockProductos = Arrays.asList(
                new Producto(1L, "Producto A", 10.0, null),
                new Producto(2L, "Producto B", 15.0, null)
        );

        when(productoRepository.findAll()).thenReturn(mockProductos);

        List<Producto> productos = productoService.obtenerProductos();

        assertEquals(2, productos.size());
        verify(productoRepository, times(1)).findAll();
    }

    /**
     * ✅ Test para obtener un producto por ID existente
     */
    @Test
    void obtenerProductoPorId_existente_devuelveProducto() {
        Producto mockProducto = new Producto(1L, "Producto X", 20.0, null);

        when(productoRepository.findById(1L)).thenReturn(Optional.of(mockProducto));

        Producto producto = productoService.obtenerProductoPorId(1L);

        assertEquals("Producto X", producto.getNombre());
        verify(productoRepository).findById(1L);
    }

    /**
     * ✅ Test para obtener un producto por ID inexistente
     */
    @Test
    void obtenerProductoPorId_noExistente_lanzaExcepcion() {
        when(productoRepository.findById(99L)).thenReturn(Optional.empty());

        ResponseStatusException ex = assertThrows(ResponseStatusException.class,
                () -> productoService.obtenerProductoPorId(99L));

        assertEquals("404 NOT_FOUND \"Producto no encontrado\"", ex.getMessage());
    }

    /**
     * ✅ Test para crear un nuevo producto
     */
    @Test
    void crearProducto_datosValidos_guardaProducto() {
        ProductoDTO dto = new ProductoDTO();
        dto.setNombre("Nuevo Producto");
        dto.setPrecio(30.0);

        Producto mockGuardado = new Producto(1L, "Nuevo Producto", 30.0, null);

        when(productoRepository.save(any(Producto.class))).thenReturn(mockGuardado);

        Producto result = productoService.crearProducto(dto);

        assertEquals("Nuevo Producto", result.getNombre());
        assertEquals(30.0, result.getPrecio());
        verify(productoRepository).save(any(Producto.class));
    }

    /**
     * ✅ Test para actualizar un producto existente
     */
    @Test
    void actualizarProducto_existente_actualizaYGuarda() {
        Producto existente = new Producto(1L, "Antiguo Nombre", 10.0, null);
        ProductoDTO dto = new ProductoDTO();
        dto.setNombre("Nombre Actualizado");
        dto.setPrecio(99.99);

        when(productoRepository.findById(1L)).thenReturn(Optional.of(existente));
        when(productoRepository.save(any(Producto.class))).thenAnswer(inv -> inv.getArgument(0));

        Producto actualizado = productoService.actualizarProducto(1L, dto);

        assertEquals("Nombre Actualizado", actualizado.getNombre());
        assertEquals(99.99, actualizado.getPrecio());
        verify(productoRepository).save(existente);
    }

    /**
     * ✅ Test para eliminar un producto por ID
     */
    @Test
    void eliminarProducto_existente_eliminaCorrectamente() {
        productoService.eliminarProducto(1L);
        verify(productoRepository).deleteById(1L);
    }
}