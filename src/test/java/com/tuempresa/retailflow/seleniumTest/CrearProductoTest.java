/*package com.tuempresa.retailflow.seleniumTest;

import com.tuempresa.retailflow.base.BaseTest;
import com.tuempresa.retailflow.pages.LoginRetailPage;
import com.tuempresa.retailflow.pages.ProductosPage;
import com.tuempresa.retailflow.testRail.TestRailClient;
import com.tuempresa.retailflow.testRail.TestRailReporter;
import org.junit.jupiter.api.Test;

import java.time.Duration;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;

public class CrearProductoTest extends BaseTest {

    @Test
    public void crearProductoExitosamenteLoAgregaALaTabla() {
        boolean passed = false;

        try {

            // 1. Login
            driver.get("http://codigoabierto.online/login");

            LoginRetailPage loginPage = new LoginRetailPage(driver);
            loginPage.iniciarSesion("JoanG101", "Stejerosam#77");

            Thread.sleep(3000);
            // 1. Ir a la página de productos
            driver.get("http://codigoabierto.online/productos");

            // 2. Crear instancia de la página
            ProductosPage productosPage = new ProductosPage(driver);
            productosPage.esperarFormularioVisible();

            // 3. Completar formulario con un nuevo producto
            String nombre = "Producto Selenium " + System.currentTimeMillis();
            String precio = "12345.67";

            productosPage.ingresarNombre(nombre);
            productosPage.ingresarPrecio(precio);
            productosPage.clickCrearProducto();

            // 4. Validar mensaje de éxito y presencia del producto en la tabla
            String mensaje = productosPage.obtenerMensajeExito();
            boolean productoEnTabla = productosPage.existeProductoConNombre(nombre);

            passed = mensaje != null && mensaje.contains("Producto creado") && productoEnTabla;

            assertTrue(passed, "❌ No se creó correctamente el producto o no apareció en la tabla.");

        } catch (Exception e) {
            e.printStackTrace();
            fail("❌ Excepción durante la prueba de creación de producto: " + e.getMessage());
        } finally {
            // 5. Reporte a TestRail
            try {
                TestRailClient client = new TestRailClient(
                        "https://codigoabiertop.testrail.io",
                        "codigo.abierto.p@gmail.com",
                        "pknkko2Hs9S8IPUANOzE-KVHY/dyV3IhGvtilMXUV"
                );

                int projectId = 3;
                int suiteId = 6;
                int caseId = 37; // ⚠️ Cambiar por el ID correcto en tu TestRail

                TestRailReporter reporter = new TestRailReporter(client, projectId, suiteId, caseId);
                reporter.reportResultPerTest(
                        "Crear producto exitosamente lo agrega a la tabla",
                        passed,
                        "E2E - Producto nuevo creado y visible en listado"
                );

                System.out.println("📡 Reporte enviado a TestRail: " + (passed ? "PASSED" : "FAILED"));

            } catch (Exception e) {
                e.printStackTrace();
                System.err.println("❌ No se pudo reportar a TestRail.");
            }
        }
    }

    @Test
    public void crearProductoConCamposVaciosMuestraError() {
        boolean passed = false;

        try {
            // 1. Login
            driver.get("http://codigoabierto.online/login");

            LoginRetailPage loginPage = new LoginRetailPage(driver);
            loginPage.iniciarSesion("JoanG101", "Stejerosam#77");

            Thread.sleep(3000); // esperar sesión

            // 2. Ir a página de productos
            driver.get("http://codigoabierto.online/productos");

            ProductosPage productosPage = new ProductosPage(driver);
            productosPage.esperarFormularioVisible();

            // 3. Dejar los campos vacíos
            productosPage.ingresarNombre("");
            productosPage.ingresarPrecio("");
            productosPage.clickCrearProducto();

            // 4. Verificar mensaje de error y que no se redirige
            String mensaje = productosPage.obtenerMensajeError();
            boolean sigueEnProductos = driver.getCurrentUrl().contains("/productos");

            passed = mensaje != null &&
                    mensaje.toLowerCase().contains("todos los campos") &&
                    sigueEnProductos;

            assertTrue(passed, "❌ No se mostró el mensaje de error con campos vacíos o se redirigió fuera de productos.");

        } catch (Exception e) {
            e.printStackTrace();
            fail("❌ Excepción inesperada durante la prueba de producto con campos vacíos: " + e.getMessage());
        } finally {
            // 5. Reporte a TestRail
            try {
                TestRailClient client = new TestRailClient(
                        "https://codigoabiertop.testrail.io",
                        "codigo.abierto.p@gmail.com",
                        "pknkko2Hs9S8IPUANOzE-KVHY/dyV3IhGvtilMXUV"
                );

                int projectId = 3;
                int suiteId = 6;
                int caseId = 37; // ⚠️ Ajusta por el ID correcto

                TestRailReporter reporter = new TestRailReporter(client, projectId, suiteId, caseId);
                reporter.reportResultPerTest(
                        "Crear producto con campos vacíos muestra error",
                        passed,
                        "E2E - Producto con campos vacíos muestra alerta de validación y no se envía"
                );

                System.out.println("📡 Reporte enviado a TestRail: " + (passed ? "PASSED" : "FAILED"));

            } catch (Exception e) {
                e.printStackTrace();
                System.err.println("❌ No se pudo reportar a TestRail.");
            }
        }
    }

}

 */
