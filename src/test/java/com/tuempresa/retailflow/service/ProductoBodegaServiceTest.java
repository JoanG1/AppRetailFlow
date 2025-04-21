package com.tuempresa.retailflow.service;

import com.tuempresa.retailflow.dto.ProductoBodegaDTO;
import com.tuempresa.retailflow.entity.*;
import com.tuempresa.retailflow.repository.*;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class ProductoBodegaServiceTest {

    @InjectMocks
    private ProductoBodegaService productoBodegaService;

    @Mock
    private ProductoBodegaRepository productoBodegaRepository;

    @Mock
    private ProductoRepository productoRepository;

    @Mock
    private BodegaRepository bodegaRepository;

    @Mock
    private SeccionRepository seccionRepository;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testObtenerProductosPorBodega() {
        Long bodegaId = 1L;
        List<ProductoBodega> mockList = List.of(new ProductoBodega());
        when(productoBodegaRepository.findByBodegaId(bodegaId)).thenReturn(mockList);

        List<ProductoBodega> result = productoBodegaService.obtenerProductosPorBodega(bodegaId);

        assertEquals(1, result.size());
        verify(productoBodegaRepository).findByBodegaId(bodegaId);
    }

    @Test
    void testObtenerProductosPorBodegaYSeccion() {
        Long bodegaId = 1L;
        Long seccionId = 2L;
        List<ProductoBodega> mockList = List.of(new ProductoBodega());
        when(productoBodegaRepository.findByBodegaIdAndSeccionId(bodegaId, seccionId)).thenReturn(mockList);

        List<ProductoBodega> result = productoBodegaService.obtenerProductosPorBodegaYSeccion(bodegaId, seccionId);

        assertEquals(1, result.size());
        verify(productoBodegaRepository).findByBodegaIdAndSeccionId(bodegaId, seccionId);
    }

    @Test
    void testAsignarProductoABodega_NuevoProducto() {
        ProductoBodegaDTO dto = new ProductoBodegaDTO(null,null,null,0);
        dto.setProductoId(1L);
        dto.setSeccionId(2L);
        dto.setStock(10);

        Producto producto = new Producto();
        Seccion seccion = new Seccion();
        Bodega bodega = new Bodega();
        seccion.setBodega(bodega);

        when(productoRepository.findById(1L)).thenReturn(Optional.of(producto));
        when(seccionRepository.findById(2L)).thenReturn(Optional.of(seccion));
        when(productoBodegaRepository.findByProductoIdAndBodegaIdAndSeccionId(anyLong(), anyLong(), anyLong()))
                .thenReturn(Optional.empty());

        when(productoBodegaRepository.save(any())).thenAnswer(inv -> inv.getArgument(0));

        ProductoBodega result = productoBodegaService.asignarProductoABodega(dto);

        assertNotNull(result);
        assertEquals(10, result.getStock());
        assertEquals(producto, result.getProducto());
        assertEquals(bodega, result.getBodega());
        assertEquals(seccion, result.getSeccion());
    }

    @Test
    void testAsignarProductoABodega_ActualizarStock() {
        Long productoId = 1L;
        Long bodegaId = 1L;
        Long seccionId = 1L;
        Integer cantidadInicial = 10;
        Integer cantidadAAsignar = 5;

        Producto producto = new Producto();
        producto.setId(productoId);

        Bodega bodega = new Bodega();
        bodega.setId(bodegaId);

        Seccion seccion = new Seccion();
        seccion.setId(seccionId);
        seccion.setBodega(bodega);

        ProductoBodega existente = new ProductoBodega();
        existente.setId(1L);
        existente.setProducto(producto);
        existente.setBodega(bodega);
        existente.setSeccion(seccion);
        existente.setStock(cantidadInicial);

        // 🔧 Mocks
        when(productoRepository.findById(productoId)).thenReturn(Optional.of(producto));
        when(seccionRepository.findById(seccionId)).thenReturn(Optional.of(seccion));
        when(productoBodegaRepository.findByProductoIdAndBodegaIdAndSeccionId(productoId, bodegaId, seccionId)).thenReturn(Optional.of(existente));
        when(productoBodegaRepository.save(any(ProductoBodega.class))).thenAnswer(invocation -> invocation.getArgument(0));

        // 📦 Crear DTO con los datos
        ProductoBodegaDTO dto = new ProductoBodegaDTO(null,null,null,0);
        dto.setProductoId(productoId);
        dto.setSeccionId(seccionId);
        dto.setStock(cantidadAAsignar);

        // 🧪 Ejecutar el método
        ProductoBodega resultado = productoBodegaService.asignarProductoABodega(dto);

        // ✅ Verificaciones
        assertNotNull(resultado);
        assertEquals(productoId, resultado.getProducto().getId());
        assertEquals(bodegaId, resultado.getBodega().getId());
        assertEquals(seccionId, resultado.getSeccion().getId());
        assertEquals(15, resultado.getStock()); // 10 inicial + 5 nuevos
    }


    @Test
    void testAsignarProductoABodega_StockNegativo() {
        ProductoBodegaDTO dto = new ProductoBodegaDTO(null,null,null,0);
        dto.setStock(-1);

        IllegalArgumentException ex = assertThrows(IllegalArgumentException.class, () ->
                productoBodegaService.asignarProductoABodega(dto));

        assertEquals("El stock no puede ser nulo ni negativo.", ex.getMessage());
    }

    @Test
    void testObtenerStockTotalPorProducto() {
        ProductoBodega mock = new ProductoBodega();
        mock.setStock(20);
        when(productoBodegaRepository.findById(1L)).thenReturn(Optional.of(mock));

        int total = productoBodegaService.obtenerStockTotalPorProducto(1L);
        assertEquals(20, total);
    }

    @Test
    void testEliminarProductoDeBodega() {
        productoBodegaService.eliminarProductoDeBodega(1L);
        verify(productoBodegaRepository).deleteById(1L);
    }
}