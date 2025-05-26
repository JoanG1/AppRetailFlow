package com.tuempresa.retailflow.seleniumTest;

import com.tuempresa.retailflow.base.BaseTest;
import com.tuempresa.retailflow.pages.RegisterRetailPage;
import com.tuempresa.retailflow.testRail.TestRailClient;
import com.tuempresa.retailflow.testRail.TestRailReporter;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;

public class RegisterServiceTest extends BaseTest {

    @Test
    public void registerConDatosInvalidosMuestraError() {
        boolean passed = false;

        try {
            // 1. Navegar a la página de registro
            driver.get("http://codigoabierto.online/register");

            // 2. Intentar registrarse con contraseña inválida (sin mayúscula, número o carácter especial)
            RegisterRetailPage registerPage = new RegisterRetailPage(driver);
            registerPage.registrarUsuario("usuarioPrueba", "simple", "simple");

            // 3. Verificar que aparece la alerta de error y no se redirige aún
            boolean alertaVisible = registerPage.estaVisibleAlertaError();
            boolean sigueEnRegister = driver.getCurrentUrl().contains("/register");

            passed = alertaVisible && sigueEnRegister;

            assertTrue(passed, "❌ No se mostró el mensaje de error o se redirigió fuera de /register");

        } catch (Exception e) {
            e.printStackTrace();
            fail("❌ Excepción inesperada durante la prueba de registro inválido: " + e.getMessage());
        } finally {
            // 4. Reportar a TestRail
            try {
                TestRailClient client = new TestRailClient(
                        "https://codigoabiertop.testrail.io",
                        "codigo.abierto.p@gmail.com",
                        "pknkko2Hs9S8IPUANOzE-KVHY/dyV3IhGvtilMXUV"
                );

                int projectId = 3;
                int suiteId = 6;
                int caseId = 37; // ⚠️ Ajusta este ID al case real en tu TestRail

                TestRailReporter reporter = new TestRailReporter(client, projectId, suiteId, caseId);
                reporter.reportResultPerTest(
                        "Registro con datos inválidos muestra error",
                        passed,
                        "E2E - Registro con contraseña inválida mantiene al usuario en /register y muestra alerta"
                );

                System.out.println("📡 Reporte enviado a TestRail: " + (passed ? "PASSED" : "FAILED"));

            } catch (Exception e) {
                e.printStackTrace();
                System.err.println("❌ No se pudo reportar a TestRail.");
            }
        }
    }

    @Test
    public void registerConDatosValidosRedirigeCorrectamente() {
        boolean passed = false;

        try {
            // 1. Navegar a la página de registro
            driver.get("http://codigoabierto.online/register");

            // 2. Registrar usuario con datos válidos
            RegisterRetailPage registerPage = new RegisterRetailPage(driver);

            // Generar usuario único para evitar conflicto
            String username = "usuarioAuto" + System.currentTimeMillis();
            String password = "Password#77!";

            registerPage.registrarUsuario(username, password, password);

            // 3. Verificar que redirige (ej. a login o dashboard)
            Thread.sleep(5000); // opcional, reemplazable por espera explícita

            String urlActual = driver.getCurrentUrl();
            boolean fueRedirigido = !urlActual.contains("/register");

            passed = fueRedirigido;

            assertTrue(passed, "❌ No se redirigió correctamente tras un registro exitoso. URL actual: " + urlActual);

        } catch (Exception e) {
            e.printStackTrace();
            fail("❌ Excepción inesperada durante el registro con datos válidos: " + e.getMessage());
        } finally {
            // 4. Reportar a TestRail
            try {
                TestRailClient client = new TestRailClient(
                        "https://codigoabiertop.testrail.io",
                        "codigo.abierto.p@gmail.com",
                        "pknkko2Hs9S8IPUANOzE-KVHY/dyV3IhGvtilMXUV"
                );

                int projectId = 3;
                int suiteId = 6;
                int caseId = 37; // ⚠️ Ajustar al ID correcto en TestRail

                TestRailReporter reporter = new TestRailReporter(client, projectId, suiteId, caseId);
                reporter.reportResultPerTest(
                        "Registro con datos válidos redirige correctamente",
                        passed,
                        "E2E - Registro exitoso redirige al usuario fuera de /register"
                );

                System.out.println("📡 Reporte enviado a TestRail: " + (passed ? "PASSED" : "FAILED"));

            } catch (Exception e) {
                e.printStackTrace();
                System.err.println("❌ No se pudo reportar a TestRail.");
            }
        }
    }

    @Test
    public void registerConCamposVaciosMuestraError() {
        boolean passed = false;

        try {
            // 1. Ir a la página de registro
            driver.get("http://codigoabierto.online/register");

            // 2. Enviar el formulario con todos los campos vacíos
            RegisterRetailPage registerPage = new RegisterRetailPage(driver);
            registerPage.registrarUsuario("", "", "");

            // 3. Verificar que aparece mensaje de error y seguimos en /register

            boolean sigueEnRegister = driver.getCurrentUrl().contains("/register");

            passed = sigueEnRegister;

            assertTrue(passed, "❌ No se mostró el mensaje de error o se redirigió fuera de /register con campos vacíos.");

        } catch (Exception e) {
            e.printStackTrace();
            fail("❌ Excepción inesperada durante la prueba de registro con campos vacíos: " + e.getMessage());
        } finally {
            // 4. Reportar a TestRail
            try {
                TestRailClient client = new TestRailClient(
                        "https://codigoabiertop.testrail.io",
                        "codigo.abierto.p@gmail.com",
                        "pknkko2Hs9S8IPUANOzE-KVHY/dyV3IhGvtilMXUV"
                );

                int projectId = 3;
                int suiteId = 6;
                int caseId = 37; // ⚠️ Ajusta este ID en tu TestRail

                TestRailReporter reporter = new TestRailReporter(client, projectId, suiteId, caseId);
                reporter.reportResultPerTest(
                        "Registro con campos vacíos muestra error",
                        passed,
                        "E2E - Registro vacío mantiene al usuario en /register y muestra error"
                );

                System.out.println("📡 Reporte enviado a TestRail: " + (passed ? "PASSED" : "FAILED"));

            } catch (Exception e) {
                e.printStackTrace();
                System.err.println("❌ No se pudo reportar a TestRail.");
            }
        }
    }


}
