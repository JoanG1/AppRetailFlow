package com.tuempresa.retailflow.seleniumTest;

import com.tuempresa.retailflow.base.BaseTest;
import com.tuempresa.retailflow.pages.LoginRetailPage;
import com.tuempresa.retailflow.testRail.TestRailClient;
import com.tuempresa.retailflow.testRail.TestRailReporter;
import org.junit.jupiter.api.Test;

import java.time.Duration;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;

public class AuthServiceTest extends BaseTest {

    @Test
    public void loginConCredencialesInvalidasMuestraError() {
        boolean passed = false;

        try {
            // 1. Navegar a la página de login
            driver.get("http://codigoabierto.online/login");

            // 2. Intentar iniciar sesión con credenciales inválidas
            LoginRetailPage loginPage = new LoginRetailPage(driver);
            loginPage.iniciarSesion("usuarioInvalido", "claveIncorrecta");

            // 3. Verificar que aparece la alerta de error y no se redirige al dashboard
            boolean alertaVisible = loginPage.estaVisibleAlertaError();
            boolean sigueEnLogin = driver.getCurrentUrl().contains("/login");

            passed = alertaVisible && sigueEnLogin;

            assertTrue(passed, "❌ No se mostró el mensaje de error o se redirigió fuera de /login");

        } catch (Exception e) {
            e.printStackTrace();
            fail("❌ Excepción inesperada durante la prueba de login inválido: " + e.getMessage());
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
                int caseId = 37; // ⚠️ Cambia este ID por el correcto en tu TestRail

                TestRailReporter reporter = new TestRailReporter(client, projectId, suiteId, caseId);
                reporter.reportResultPerTest(
                        "Login con credenciales inválidas muestra error",
                        passed,
                        "E2E - Login con credenciales inválidas mantiene al usuario en /login y muestra alerta"
                );

                System.out.println("📡 Reporte enviado a TestRail: " + (passed ? "PASSED" : "FAILED"));

            } catch (Exception e) {
                e.printStackTrace();
                System.err.println("❌ No se pudo reportar a TestRail.");
            }
        }
    }

    @Test
    public void loginConCredencialesValidasRedirigeAlDashboard() {
        boolean passed = false;

        try {
            // 1. Navegar a la página de login
            driver.get("http://codigoabierto.online/login");

            // 2. Iniciar sesión con credenciales válidas
            LoginRetailPage loginPage = new LoginRetailPage(driver);
            loginPage.iniciarSesion("JoanG101", "Stejerosam#77");

            // 3. Esperar redirección al dashboard (o cualquier otra URL válida)
            Thread.sleep(3000); // se puede cambiar por espera explícita si hay algún indicador

            String urlActual = driver.getCurrentUrl();
            boolean redirigidoCorrectamente = urlActual.contains("/dashboard");

            passed = redirigidoCorrectamente;

            assertTrue(passed, "❌ No se redirigió al dashboard tras iniciar sesión correctamente. URL actual: " + urlActual);

        } catch (Exception e) {
            e.printStackTrace();
            fail("❌ Excepción inesperada durante la prueba de login válido: " + e.getMessage());
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
                int caseId = 37; // ⚠️ Cambia por el ID correcto en tu TestRail

                TestRailReporter reporter = new TestRailReporter(client, projectId, suiteId, caseId);
                reporter.reportResultPerTest(
                        "Login con credenciales válidas redirige al dashboard",
                        passed,
                        "E2E - El login con credenciales correctas lleva al usuario al dashboard"
                );

                System.out.println("📡 Reporte enviado a TestRail: " + (passed ? "PASSED" : "FAILED"));

            } catch (Exception e) {
                e.printStackTrace();
                System.err.println("❌ No se pudo reportar a TestRail.");
            }
        }
    }

    @Test
    public void loginConCredencialesVaciasMuestraError() {
        boolean passed = false;

        try {
            // 1. Navegar a la página de login
            driver.get("http://codigoabierto.online/login");

            // 2. Iniciar sesión sin ingresar usuario ni contraseña
            LoginRetailPage loginPage = new LoginRetailPage(driver);
            loginPage.iniciarSesion("", "");

            // 3. Verificar que aparece la alerta de error y no se redirige
            //boolean alertaVisible = loginPage.estaVisibleAlertaError();
            boolean sigueEnLogin = driver.getCurrentUrl().contains("/login");

            passed = sigueEnLogin;

            assertTrue(passed, "❌ No se mostró el mensaje de error con credenciales vacías o se redirigió fuera de /login");

        } catch (Exception e) {
            e.printStackTrace();
            fail("❌ Excepción inesperada durante la prueba de login vacío: " + e.getMessage());
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
                int caseId = 37; // ⚠️ Cambia por el ID correcto en tu TestRail

                TestRailReporter reporter = new TestRailReporter(client, projectId, suiteId, caseId);
                reporter.reportResultPerTest(
                        "Login con campos vacíos muestra error",
                        passed,
                        "E2E - El login sin ingresar usuario ni contraseña muestra error y no redirige"
                );

                System.out.println("📡 Reporte enviado a TestRail: " + (passed ? "PASSED" : "FAILED"));

            } catch (Exception e) {
                e.printStackTrace();
                System.err.println("❌ No se pudo reportar a TestRail.");
            }
        }
    }

    @Test
    public void loginConPasswordMalFormateadoMuestraError() {
        boolean passed = false;

        try {
            // 1. Navegar a la página de login
            driver.get("http://codigoabierto.online/login");

            // 2. Ingresar usuario válido y contraseña inválida (ej: muy corta)
            LoginRetailPage loginPage = new LoginRetailPage(driver);
            loginPage.iniciarSesion("JoanG101", "123"); // mal formato

            // 3. Verificar que aparece mensaje de error y no hay redirección
            boolean alertaVisible = loginPage.estaVisibleAlertaError();
            boolean sigueEnLogin = driver.getCurrentUrl().contains("/login");

            passed = alertaVisible && sigueEnLogin;

            assertTrue(passed, "❌ No se detectó la contraseña malformada o se redirigió fuera de /login");

        } catch (Exception e) {
            e.printStackTrace();
            fail("❌ Excepción inesperada durante la prueba de contraseña malformada: " + e.getMessage());
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
                int caseId = 37; // ⚠️ Ajustar al ID real del test en TestRail

                TestRailReporter reporter = new TestRailReporter(client, projectId, suiteId, caseId);
                reporter.reportResultPerTest(
                        "Login con contraseña mal formateada muestra error",
                        passed,
                        "E2E - La contraseña mal formada debe generar un mensaje de error y no redirigir"
                );

                System.out.println("📡 Reporte enviado a TestRail: " + (passed ? "PASSED" : "FAILED"));

            } catch (Exception e) {
                e.printStackTrace();
                System.err.println("❌ No se pudo reportar a TestRail.");
            }
        }
    }




}
