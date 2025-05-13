package com.tuempresa.retailflow.service;

import com.tuempresa.retailflow.dto.SurtidoDTO;
import com.tuempresa.retailflow.dto.SurtidoProductoDTO;
import com.tuempresa.retailflow.entity.*;
import com.tuempresa.retailflow.repository.*;
import com.tuempresa.retailflow.testRail.TestRailClient;
import com.tuempresa.retailflow.testRail.TestRailReporter;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import org.springframework.data.domain.*;

import java.time.LocalDateTime;
import java.util.*;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.mockito.Mockito.*;

class SurtidoServiceTest {

    @Mock
    private SurtidoRepository surtidoRepository;

    @Mock
    private ProductoBodegaRepository productoBodegaRepository;

    @Mock
    private ProductoRepository productoRepository;

    @Mock
    private ProductoLocalRepository productoLocalRepository;

    @Mock
    private LocalRepository localRepository;

    @InjectMocks
    private SurtidoService surtidoService;

    @BeforeEach
    void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testObtenerHistorialSurtidos() {

        boolean passed = false;
        try{

            Surtido s1 = new Surtido(1L, new ArrayList<>(), LocalDateTime.now());
            when(surtidoRepository.findAll()).thenReturn(List.of(s1));

            List<Surtido> resultado = surtidoService.obtenerHistorialSurtidos();

            assertThat(resultado).hasSize(1);

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
                        "Obtener Historial Surtido",
                        passed,
                        "Automatizado - Verifica que se obtenga correctamente el historial surtido"
                );

            }catch (Exception e){
                e.printStackTrace();
            }
        }

    }

    @Test
    void testObtenerTopSurtidos() {

        boolean passed = false;
        try{

            Surtido s1 = new Surtido(1L, new ArrayList<>(), LocalDateTime.now());
            Page<Surtido> pagina = new PageImpl<>(List.of(s1));
            when(surtidoRepository.findAll(any(Pageable.class))).thenReturn(pagina);

            List<Surtido> resultado = surtidoService.obtenerTopSurtidos();

            assertThat(resultado).hasSize(1);

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
                        "Obtener top surtido",
                        passed,
                        "Automatizado - Verifica que se obtenga los primeros surtidos correctamente"
                );

            }catch (Exception e){
                e.printStackTrace();
            }
        }

    }

    @Test
    void testContarSurtidos() {

        boolean passed = false;
        try{

            when(surtidoRepository.count()).thenReturn(10L);

            long count = surtidoService.contarSurtidos();

            assertThat(count).isEqualTo(10L);

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
                        "Contar surtidos",
                        passed,
                        "Automatizado - Verifica que se cuenten correctamente los surtidos"
                );

            }catch (Exception e){
                e.printStackTrace();
            }
        }

    }

    @Test
    void testCrearSurtido() {

        boolean passed = false;
        try{

            // DTO de entrada
            SurtidoProductoDTO productoDTO = new SurtidoProductoDTO();
            productoDTO.setProductoId(1L);
            productoDTO.setCantidad(5);
            SurtidoDTO surtidoDTO = new SurtidoDTO();
            surtidoDTO.setFechaSurtido(LocalDateTime.now());
            surtidoDTO.setProductos(List.of(productoDTO));

            Long localId = 1L;

            // Mocks de entidades
            Producto producto = new Producto();
            producto.setId(1L);

            ProductoBodega productoBodega = new ProductoBodega();
            productoBodega.setProducto(producto);
            productoBodega.setStock(10);

            Local local = new Local();
            local.setId(localId);

            ProductoLocal productoLocal = new ProductoLocal();
            productoLocal.setProducto(producto);
            productoLocal.setLocal(local);
            productoLocal.setStock(3);

            // Simular llamadas
            when(localRepository.findById(localId)).thenReturn(Optional.of(local));
            when(productoRepository.findById(1L)).thenReturn(Optional.of(producto));
            when(productoBodegaRepository.findByProductoId(1L)).thenReturn(productoBodega);
            when(productoLocalRepository.findByProductoIdAndLocalId(1L, localId)).thenReturn(Optional.of(productoLocal));
            when(surtidoRepository.save(any(Surtido.class))).thenAnswer(invocation -> invocation.getArgument(0));

            Surtido creado = surtidoService.crearSurtido(surtidoDTO, localId);

            assertThat(creado).isNotNull();
            assertThat(creado.getProductosSurtidos()).hasSize(1);

            verify(productoBodegaRepository).save(any());
            verify(productoLocalRepository).save(any());

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
                        "Crear surtido",
                        passed,
                        "Automatizado - Verifica que se cree correctamente un surtido"
                );

            }catch (Exception e){
                e.printStackTrace();
            }
        }

    }

    @Test
    void testCrearSurtido_conStockInsuficiente() {
        boolean passed = false;
        try{

            SurtidoProductoDTO productoDTO = new SurtidoProductoDTO();
            productoDTO.setProductoId(1L);
            productoDTO.setCantidad(100); // más que el stock

            SurtidoDTO surtidoDTO = new SurtidoDTO();
            surtidoDTO.setFechaSurtido(LocalDateTime.now());
            surtidoDTO.setProductos(List.of(productoDTO));

            Long localId = 1L;
            Producto producto = new Producto();
            producto.setId(1L);
            ProductoBodega productoBodega = new ProductoBodega();
            productoBodega.setProducto(producto);
            productoBodega.setStock(10);
            Local local = new Local();
            local.setId(localId);

            when(localRepository.findById(localId)).thenReturn(Optional.of(local));
            when(productoRepository.findById(1L)).thenReturn(Optional.of(producto));
            when(productoBodegaRepository.findByProductoId(1L)).thenReturn(productoBodega);

            assertThatThrownBy(() -> surtidoService.crearSurtido(surtidoDTO, localId))
                    .isInstanceOf(RuntimeException.class)
                    .hasMessageContaining("Stock insuficiente");

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
                        "Creacion surtido sin stock",
                        passed,
                        "Automatizado - Verifica que no se cree un surtido sin stock de producto"
                );

            }catch (Exception e){
                e.printStackTrace();
            }
        }

    }

    @Test
    void testCrearSurtido_productoNoExiste() {

        boolean passed = false;
        try{

            SurtidoProductoDTO productoDTO = new SurtidoProductoDTO();
            productoDTO.setProductoId(999L);
            productoDTO.setCantidad(1);

            SurtidoDTO surtidoDTO = new SurtidoDTO();
            surtidoDTO.setFechaSurtido(LocalDateTime.now());
            surtidoDTO.setProductos(List.of(productoDTO));

            when(localRepository.findById(anyLong())).thenReturn(Optional.of(new Local()));
            when(productoRepository.findById(999L)).thenReturn(Optional.empty());

            assertThatThrownBy(() -> surtidoService.crearSurtido(surtidoDTO, 1L))
                    .isInstanceOf(RuntimeException.class)
                    .hasMessageContaining("Producto no encontrado");

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
                        "Creacion de surtido sin existir producto",
                        passed,
                        "Automatizado - Verifica que no se cree correctamente un surtido sin existir el producto asociado"
                );

            }catch (Exception e){
                e.printStackTrace();
            }
        }

    }
}