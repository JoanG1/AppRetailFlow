/*package com.tuempresa.retailflow.seleniumTest;

import com.tuempresa.retailflow.base.BaseTest;
import com.tuempresa.retailflow.pages.LocalesPage;
import com.tuempresa.retailflow.pages.LoginRetailPage;
import com.tuempresa.retailflow.testRail.TestRailClient;
import com.tuempresa.retailflow.testRail.TestRailReporter;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;

public class CrearLocalTest extends BaseTest {

    @Test
    public void crearLocalExitosamenteYSeMuestraEnListado() {
        boolean passed = false;

        try {

            // 1. Login
            driver.get("http://codigoabierto.online/login");

            LoginRetailPage loginPage = new LoginRetailPage(driver);
            loginPage.iniciarSesion("JoanG101", "Stejerosam#77");

            Thread.sleep(3000);
            // 1. Ir a la página de locales
            driver.get("http://codigoabierto.online/locales");

            Thread.sleep(3000);

            // 2. Crear instancia de la página
            LocalesPage localesPage = new LocalesPage(driver);
            localesPage.esperarFormularioVisible();

            // 3. Ingresar un nombre de local único
            String nombreLocal = "Local Selenium " + System.currentTimeMillis();
            localesPage.ingresarNombreLocal(nombreLocal);
            localesPage.clickAgregarLocal();

            // 4. Verificar mensaje de éxito y presencia en lista
            String mensaje = localesPage.obtenerMensaje();
            boolean estaEnLista = localesPage.existeLocalConNombre(nombreLocal);

            passed = mensaje != null && mensaje.contains("Local creado") && estaEnLista;

            assertTrue(passed, "❌ El local no fue creado correctamente o no aparece en la lista.");

        } catch (Exception e) {
            e.printStackTrace();
            fail("❌ Excepción inesperada durante la prueba de creación de local: " + e.getMessage());
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
                int caseId = 37; // ⚠️ Cambia este ID por el correcto en tu TestRail

                TestRailReporter reporter = new TestRailReporter(client, projectId, suiteId, caseId);
                reporter.reportResultPerTest(
                        "Crear local exitosamente lo muestra en la lista",
                        passed,
                        "E2E - Validación de alta exitosa de un local nuevo"
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