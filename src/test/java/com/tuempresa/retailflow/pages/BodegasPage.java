package com.tuempresa.retailflow.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class BodegasPage {

    private final WebDriver driver;
    private final WebDriverWait wait;

    // Selectores
    private final By inputNombreBodega = By.id("input-nombre-bodega");
    private final By botonToggleSecciones = By.id("boton-toggle-secciones");
    private final By inputNombreSeccion = By.id("input-nombre-seccion");
    private final By botonAgregarSeccion = By.id("boton-agregar-seccion");
    private final By botonCrearBodega = By.id("boton-crear-bodega");
    private final By alertaMensaje = By.id("alerta-mensaje-bodega");

    public BodegasPage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(20));
    }

    public void esperarPaginaCargada() {
        wait.until(ExpectedConditions.visibilityOfElementLocated(inputNombreBodega));
    }

    public void ingresarNombreBodega(String nombre) {
        WebElement campo = wait.until(ExpectedConditions.visibilityOfElementLocated(inputNombreBodega));
        campo.clear();
        campo.sendKeys(nombre);
    }

    public void mostrarSecciones() {
        wait.until(ExpectedConditions.elementToBeClickable(botonToggleSecciones)).click();
    }

    public void agregarSeccion(String nombreSeccion) {
        WebElement input = wait.until(ExpectedConditions.visibilityOfElementLocated(inputNombreSeccion));
        input.clear();
        input.sendKeys(nombreSeccion);
        wait.until(ExpectedConditions.elementToBeClickable(botonAgregarSeccion)).click();
    }

    public void crearBodega() {
        wait.until(ExpectedConditions.elementToBeClickable(botonCrearBodega)).click();
    }

    public String obtenerMensaje() {
        try {
            WebElement alerta = wait.until(ExpectedConditions.visibilityOfElementLocated(alertaMensaje));
            return alerta.getText();
        } catch (Exception e) {
            return null;
        }
    }
}
