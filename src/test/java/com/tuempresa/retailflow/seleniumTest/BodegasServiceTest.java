package com.tuempresa.retailflow.seleniumTest;

import com.tuempresa.retailflow.base.BaseTest;
import com.tuempresa.retailflow.pages.BodegasPage;
import com.tuempresa.retailflow.pages.LoginRetailPage;
import com.tuempresa.retailflow.testRail.TestRailClient;
import com.tuempresa.retailflow.testRail.TestRailReporter;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;

public class BodegasServiceTest extends BaseTest {

    @Test
    public void crearBodegaConSeccionYMostrarMensaje() {
        boolean passed = false;

        try {
            // 1. Login
            driver.get("http://codigoabierto.online/login");

            LoginRetailPage loginPage = new LoginRetailPage(driver);
            loginPage.iniciarSesion("JoanG101", "Stejerosam#77");

            Thread.sleep(3000);
            // 2. Ir a /bodegas
            driver.get("http://codigoabierto.online/bodegas");

            BodegasPage bodegasPage = new BodegasPage(driver);

            // Esperar que la página esté lista
            bodegasPage.esperarPaginaCargada();

            String nombreBodega = "TestBodega_" + System.currentTimeMillis();
            String nombreSeccion = "Seccion W";

            // 3. Crear bodega con sección
            bodegasPage.ingresarNombreBodega(nombreBodega);
            bodegasPage.mostrarSecciones();
            bodegasPage.agregarSeccion(nombreSeccion);
            bodegasPage.crearBodega();

            // 4. Validar mensaje de éxito
            String mensaje = bodegasPage.obtenerMensaje();
            passed = mensaje != null && mensaje.contains("✅ ¡Bodega creada exitosamente");

            assertTrue(passed, "❌ No se mostró mensaje de éxito tras crear bodega");

        } catch (Exception e) {
            e.printStackTrace();
            fail("❌ Excepción inesperada: " + e.getMessage());
        } finally {
            // 5. Reportar a TestRail
            try {
                TestRailClient client = new TestRailClient(
                        "https://codigoabiertop.testrail.io",
                        "codigo.abierto.p@gmail.com",
                        "pknkko2Hs9S8IPUANOzE-KVHY/dyV3IhGvtilMXUV"
                );

                int projectId = 3;
                int suiteId = 6;
                int caseId = 37;

                TestRailReporter reporter = new TestRailReporter(client, projectId, suiteId, caseId);
                reporter.reportResultPerTest(
                        "Crear bodega con secciones muestra mensaje de éxito",
                        passed,
                        "E2E - Se navega a /bodegas, se crea una bodega con secciones y se valida el mensaje de éxito"
                );

                System.out.println("📡 Reporte enviado a TestRail: " + (passed ? "PASSED" : "FAILED"));

            } catch (Exception e) {
                e.printStackTrace();
                System.err.println("❌ No se pudo reportar a TestRail.");
            }
        }
    }
}
