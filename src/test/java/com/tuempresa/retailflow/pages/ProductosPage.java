package com.tuempresa.retailflow.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.List;

public class ProductosPage {

    private final WebDriver driver;
    private final WebDriverWait wait;

    // Selectores (basados en tu JSX y clases comunes de MUI)
    private final By inputNombre = By.id("input-nombre-producto");
    private final By inputPrecio = By.id("input-precio-producto");
    private final By botonCrearProducto = By.id("boton-crear-producto");
    private final By alertaExito = By.id("alerta-exito-producto");
    private final By alertaError = By.id("alerta-error-producto");
    private final By filasProductos = By.cssSelector("#tabla-productos tbody tr");

    public ProductosPage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(15));
    }

    public void esperarFormularioVisible() {
        wait.until(ExpectedConditions.visibilityOfElementLocated(inputNombre));
        wait.until(ExpectedConditions.visibilityOfElementLocated(inputPrecio));
    }

    public void ingresarNombre(String nombre) {
        WebElement nombreInput = wait.until(ExpectedConditions.elementToBeClickable(inputNombre));
        nombreInput.clear();
        nombreInput.sendKeys(nombre);
    }

    public void ingresarPrecio(String precio) {
        WebElement precioInput = wait.until(ExpectedConditions.elementToBeClickable(inputPrecio));
        precioInput.clear();
        precioInput.sendKeys(precio);
    }

    public void clickCrearProducto() {
        WebElement boton = wait.until(ExpectedConditions.elementToBeClickable(botonCrearProducto));
        boton.click();
    }

    public String obtenerMensajeExito() {
        try {
            WebElement alerta = wait.until(ExpectedConditions.visibilityOfElementLocated(alertaExito));
            return alerta.getText();
        } catch (Exception e) {
            return null;
        }
    }

    public String obtenerMensajeError() {
        try {
            WebElement alerta = wait.until(ExpectedConditions.visibilityOfElementLocated(alertaError));
            return alerta.getText();
        } catch (Exception e) {
            return null;
        }
    }

    public int obtenerCantidadProductos() {
        List<WebElement> filas = wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(filasProductos));
        return filas.size();
    }

    public boolean existeProductoConNombre(String nombreBuscado) {
        List<WebElement> filas = wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(filasProductos));
        return filas.stream()
                .anyMatch(fila -> fila.getText().toLowerCase().contains(nombreBuscado.toLowerCase()));
    }
}
