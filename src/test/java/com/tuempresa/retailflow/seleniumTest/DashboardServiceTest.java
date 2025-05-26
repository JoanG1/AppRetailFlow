/*package com.tuempresa.retailflow.seleniumTest;

import com.tuempresa.retailflow.base.BaseTest;
import com.tuempresa.retailflow.pages.DashboardPage;
import com.tuempresa.retailflow.pages.LoginRetailPage;
import com.tuempresa.retailflow.testRail.TestRailClient;
import com.tuempresa.retailflow.testRail.TestRailReporter;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;

public class DashboardServiceTest extends BaseTest {

    @Test
    public void dashboardCargaYNavegaCorrectamente() {
        boolean passed = false;

        try {
            // 1. Ir al login
            driver.get("http://codigoabierto.online/login");

            // 2. Iniciar sesión con usuario válido
            LoginRetailPage loginPage = new LoginRetailPage(driver);
            loginPage.iniciarSesion("JoanG101", "Stejerosam#77");

            // 3. Validar que estamos en el Dashboard
            DashboardPage dashboardPage = new DashboardPage(driver);
            boolean dashboardVisible = dashboardPage.estaVisibleElDashboard();
            assertTrue(dashboardVisible, "❌ El dashboard no se cargó correctamente");

            // 4. Navegar a cada sección y validar la URL
            dashboardPage.irABodegas();
            assertTrue(driver.getCurrentUrl().contains("/bodegas"), "❌ No se redirigió a /bodegas");
            driver.get("http://codigoabierto.online/dashboard");
            dashboardPage.irALocales();
            assertTrue(driver.getCurrentUrl().contains("/locales"), "❌ No se redirigió a /locales");
            driver.get("http://codigoabierto.online/dashboard");
            dashboardPage.irAProductos();
            assertTrue(driver.getCurrentUrl().contains("/productos"), "❌ No se redirigió a /productos");
            driver.get("http://codigoabierto.online/dashboard");
            dashboardPage.irASurtidos();
            assertTrue(driver.getCurrentUrl().contains("/surtidos"), "❌ No se redirigió a /surtidos");
            driver.get("http://codigoabierto.online/dashboard");
            dashboardPage.irARegistrarVenta();
            assertTrue(driver.getCurrentUrl().contains("/venta"), "❌ No se redirigió a /venta");
            driver.get("http://codigoabierto.online/dashboard");
            // 5. Cerrar sesión
            dashboardPage.cerrarSesion();
            boolean redirigidoLogin = dashboardPage.redirigidoAlLogin();
            assertTrue(redirigidoLogin, "❌ No se redirigió a /login al cerrar sesión");

            passed = dashboardVisible && redirigidoLogin;

        } catch (Exception e) {
            e.printStackTrace();
            fail("❌ Error en test de navegación en dashboard: " + e.getMessage());
        } finally {
            // 6. Reporte a TestRail
            try {
                TestRailClient client = new TestRailClient(
                        "https://codigoabiertop.testrail.io",
                        "codigo.abierto.p@gmail.com",
                        "pknkko2Hs9S8IPUANOzE-KVHY/dyV3IhGvtilMXUV"
                );

                int projectId = 3;
                int suiteId = 6;
                int caseId = 37; // ⚠️ Cambia este ID según tu TestRail

                TestRailReporter reporter = new TestRailReporter(client, projectId, suiteId, caseId);
                reporter.reportResultPerTest(
                        "Dashboard carga y navega correctamente por todas las secciones",
                        passed,
                        "E2E - Login exitoso accede a Dashboard, se navega por todas las secciones y se cierra sesión correctamente"
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
