package com.tuempresa.retailflow.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class DashboardPage {

    private final WebDriver driver;
    private final WebDriverWait wait;

    // Elementos visibles
    private final By tituloPanel = By.id("titulo-panel-control");
    private final By tituloBienvenida = By.id("bienvenida-dashboard");

    // Botones de navegación
    private final By btnInicio = By.id("nav-inicio");
    private final By btnBodegas = By.id("nav-bodegas");
    private final By btnLocales = By.id("nav-locales");
    private final By btnProductos = By.id("nav-productos");
    private final By btnSurtidos = By.id("nav-surtidos");
    private final By btnRegistrarVenta = By.id("nav-venta");
    private final By btnCerrarSesion = By.id("boton-cerrar-sesion");

    public DashboardPage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
    }

    public boolean estaVisibleElDashboard() {
        try {
            WebElement panel = wait.until(ExpectedConditions.visibilityOfElementLocated(tituloPanel));
            WebElement bienvenida = wait.until(ExpectedConditions.visibilityOfElementLocated(tituloBienvenida));
            return panel.isDisplayed() && bienvenida.isDisplayed();
        } catch (Exception e) {
            return false;
        }
    }

    public void irAInicio() {
        wait.until(ExpectedConditions.elementToBeClickable(btnInicio)).click();
    }

    public void irABodegas() {
        wait.until(ExpectedConditions.elementToBeClickable(btnBodegas)).click();
    }

    public void irALocales() {
        wait.until(ExpectedConditions.elementToBeClickable(btnLocales)).click();
    }

    public void irAProductos() {
        wait.until(ExpectedConditions.elementToBeClickable(btnProductos)).click();
    }

    public void irASurtidos() {
        wait.until(ExpectedConditions.elementToBeClickable(btnSurtidos)).click();
    }

    public void irARegistrarVenta() {
        wait.until(ExpectedConditions.elementToBeClickable(btnRegistrarVenta)).click();
    }

    public void cerrarSesion() {
        wait.until(ExpectedConditions.elementToBeClickable(btnCerrarSesion)).click();
    }

    public boolean redirigidoAlLogin() {
        try {
            return wait.until(ExpectedConditions.urlContains("/login"));
        } catch (Exception e) {
            return false;
        }
    }
}

