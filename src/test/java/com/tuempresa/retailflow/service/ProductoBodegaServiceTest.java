package com.tuempresa.retailflow.service;

import com.tuempresa.retailflow.dto.ProductoBodegaDTO;
import com.tuempresa.retailflow.entity.*;
import com.tuempresa.retailflow.repository.*;
import com.tuempresa.retailflow.testRail.TestRailClient;
import com.tuempresa.retailflow.testRail.TestRailReporter;
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
        boolean passed = false;

        try {
            Long bodegaId = 1L;
            List<ProductoBodega> mockList = List.of(new ProductoBodega());
            when(productoBodegaRepository.findByBodegaId(bodegaId)).thenReturn(mockList);

            List<ProductoBodega> result = productoBodegaService.obtenerProductosPorBodega(bodegaId);

            assertEquals(1, result.size());
            verify(productoBodegaRepository).findByBodegaId(bodegaId);

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
                        "Obtener productos por bodega",
                        passed,
                        "Automatizado - Verifica que se obtengan los productos por ID de bodega"
                );
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }


    @Test
    void testObtenerProductosPorBodegaYSeccion() {
        boolean passed = false;

        try {
            Long bodegaId = 1L;
            Long seccionId = 2L;
            List<ProductoBodega> mockList = List.of(new ProductoBodega());
            when(productoBodegaRepository.findByBodegaIdAndSeccionId(bodegaId, seccionId)).thenReturn(mockList);

            List<ProductoBodega> result = productoBodegaService.obtenerProductosPorBodegaYSeccion(bodegaId, seccionId);

            assertEquals(1, result.size());
            verify(productoBodegaRepository).findByBodegaIdAndSeccionId(bodegaId, seccionId);

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
                        "Obtener productos por bodega y sección",
                        passed,
                        "Automatizado - Valida la obtención de productos por ID de bodega y sección"
                );
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }


    @Test
    void testAsignarProductoABodega_ActualizarStock() {
        boolean passed = false;

        try {
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
            when(productoBodegaRepository.findByProductoIdAndBodegaIdAndSeccionId(productoId, bodegaId, seccionId))
                    .thenReturn(Optional.of(existente));
            when(productoBodegaRepository.save(any(ProductoBodega.class))).thenAnswer(invocation -> invocation.getArgument(0));

            // 📦 Crear DTO con los datos
            ProductoBodegaDTO dto = new ProductoBodegaDTO(null, null, null, 0);
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
                TestRailReporter reporter = new TestRailReporter(client, 3, 5, 36); // Reemplaza 38 por el ID real del caso si es otro
                reporter.reportResultPerTest(
                        "Asignar producto a bodega - actualizar stock",
                        passed,
                        "Automatizado - Valida la actualización del stock cuando ya existe una relación producto-bodega-sección"
                );
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }



    @Test
    void testAsignarProductoABodega_StockNegativo() {
        boolean passed = false;

        try {
            ProductoBodegaDTO dto = new ProductoBodegaDTO(null, null, null, 0);
            dto.setStock(-1);

            IllegalArgumentException ex = assertThrows(IllegalArgumentException.class, () ->
                    productoBodegaService.asignarProductoABodega(dto));

            assertEquals("El stock no puede ser nulo ni negativo.", ex.getMessage());

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
                        "Asignar producto a bodega - stock negativo",
                        passed,
                        "Automatizado - Verifica que se lanza excepción al asignar stock negativo"
                );
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }


    @Test
    void testObtenerStockTotalPorProducto() {
        boolean passed = false;

        try {
            ProductoBodega mock = new ProductoBodega();
            mock.setStock(20);
            when(productoBodegaRepository.findById(1L)).thenReturn(Optional.of(mock));

            int total = productoBodegaService.obtenerStockTotalPorProducto(1L);
            assertEquals(20, total);

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
                        "Obtener stock total por producto",
                        passed,
                        "Automatizado - Verifica el stock total consultado por ID de producto"
                );
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }


    @Test
    void testEliminarProductoDeBodega() {
        boolean passed = false;

        try {
            productoBodegaService.eliminarProductoDeBodega(1L);
            verify(productoBodegaRepository).deleteById(1L);
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
                        "Eliminar producto de bodega",
                        passed,
                        "Automatizado - Verifica que se elimine correctamente un producto de la bodega por ID"
                );
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

}