package com.tuempresa.retailflow.service;

import com.tuempresa.retailflow.dto.EditarProductoLocalDTO;
import com.tuempresa.retailflow.dto.ProductoLocalDTO;
import com.tuempresa.retailflow.entity.Local;
import com.tuempresa.retailflow.entity.Producto;
import com.tuempresa.retailflow.entity.ProductoLocal;
import com.tuempresa.retailflow.repository.LocalRepository;
import com.tuempresa.retailflow.repository.ProductoLocalRepository;
import com.tuempresa.retailflow.repository.ProductoRepository;
import com.tuempresa.retailflow.testRail.TestRailClient;
import com.tuempresa.retailflow.testRail.TestRailReporter;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;

import java.util.*;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.*;

class ProductoLocalServiceTest {

    @Mock
    private ProductoLocalRepository productoLocalRepository;

    @Mock
    private ProductoRepository productoRepository;

    @Mock
    private LocalRepository localRepository;

    @InjectMocks
    private ProductoLocalService productoLocalService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testObtenerProductosPorLocal() {
        boolean passed = false;

        try {
            Long localId = 1L;
            Producto producto = new Producto();
            producto.setId(100L);
            Local local = new Local();
            local.setId(localId);

            ProductoLocal pl = new ProductoLocal(1L, producto, local, 50);

            when(productoLocalRepository.findByLocalId(localId)).thenReturn(List.of(pl));

            List<ProductoLocalDTO> resultado = productoLocalService.obtenerProductosPorLocal(localId);

            assertThat(resultado).hasSize(1);
            assertThat(resultado.get(0).getProductoId()).isEqualTo(100L);
            assertThat(resultado.get(0).getLocalId()).isEqualTo(localId);
            assertThat(resultado.get(0).getStock()).isEqualTo(50);

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
                        "Obtener productos por local",
                        passed,
                        "Automatizado - Verifica la conversión y retorno correcto de productos por local"
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
            Long productoId = 100L;
            Producto producto = new Producto();
            producto.setId(productoId);
            Local local = new Local();
            local.setId(1L);

            ProductoLocal pl1 = new ProductoLocal(1L, producto, local, 30);

            when(productoLocalRepository.findById(productoId)).thenReturn(Optional.of(pl1));

            int stock = productoLocalService.obtenerStockTotalPorProducto(productoId);

            assertThat(stock).isEqualTo(30);

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
                        "Obtener stock total por producto (Local)",
                        passed,
                        "Automatizado - Verifica la obtención del stock total de un producto desde el local"
                );
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }


    @Test
    void testEliminarProductoDeLocal() {
        boolean passed = false;

        try {
            Long id = 999L;

            productoLocalService.eliminarProductoDeLocal(id);

            verify(productoLocalRepository, times(1)).deleteById(id);

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
                        "Eliminar producto de local",
                        passed,
                        "Automatizado - Verifica la eliminación de un producto del local por ID"
                );
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }


    @Test
    void testCrearProductoLocal() {
        boolean passed = false;

        try {
            ProductoLocalDTO dto = new ProductoLocalDTO(1L, 100L, 200L, 60);

            Producto producto = new Producto();
            producto.setId(100L);
            Local local = new Local();
            local.setId(200L);

            when(productoRepository.findById(100L)).thenReturn(Optional.of(producto));
            when(localRepository.findById(200L)).thenReturn(Optional.of(local));

            productoLocalService.crearProductoLocal(dto);

            ArgumentCaptor<ProductoLocal> captor = ArgumentCaptor.forClass(ProductoLocal.class);
            verify(productoLocalRepository).save(captor.capture());

            ProductoLocal guardado = captor.getValue();
            assertThat(guardado.getStock()).isEqualTo(60);
            assertThat(guardado.getProducto().getId()).isEqualTo(100L);
            assertThat(guardado.getLocal().getId()).isEqualTo(200L);

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
                        "Crear producto local",
                        passed,
                        "Automatizado - Verifica que se cree correctamente un producto asociado a un local"
                );
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }


    @Test
    void testActualizarProductoLocal_Existe() {

        boolean passed = false;
        try{

            Long id = 1L;
            EditarProductoLocalDTO dto = new EditarProductoLocalDTO(99);

            Producto producto = new Producto();
            producto.setId(100L);
            Local local = new Local();
            local.setId(200L);

            ProductoLocal productoLocal = new ProductoLocal(id, producto, local, 50);

            when(productoLocalRepository.findById(id)).thenReturn(Optional.of(productoLocal));
            when(productoLocalRepository.save(any())).thenReturn(productoLocal);

            ProductoLocal actualizado = productoLocalService.actualizarProductoLocal(id, dto);

            assertThat(actualizado.getStock()).isEqualTo(99);

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
                        "Actualizar producto local",
                        passed,
                        "Automatizado - Verifica que se actualice correctamente un producto asociado a un local"
                );

            }catch (Exception e){
                e.printStackTrace();
            }
        }

    }

    @Test
    void testActualizarProductoLocal_NoExiste() {

        boolean passed = false;
        try{

            Long id = 1L;
            EditarProductoLocalDTO dto = new EditarProductoLocalDTO(99);

            when(productoLocalRepository.findById(id)).thenReturn(Optional.empty());

            assertThatThrownBy(() -> productoLocalService.actualizarProductoLocal(id, dto))
                    .isInstanceOf(RuntimeException.class)
                    .hasMessageContaining("Bodega no encontrada");

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
                        "Actualizar producto local no existente",
                        passed,
                        "Automatizado - Verifica que se permita actualizar un producto no existente"
                );

            }catch (Exception e){
                e.printStackTrace();
            }
        }

    }
}