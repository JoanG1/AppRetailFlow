package com.tuempresa.retailflow.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class RegisterRetailPage {

    private final WebDriver driver;
    private final WebDriverWait wait;

    // Localizadores
    private final By inputUsuario = By.id("input-usuario");
    private final By inputClave = By.id("input-clave");
    private final By inputConfirmarClave = By.id("input-confirmar-clave");
    private final By botonRegistro = By.id("boton-registro");
    private final By botonIrLogin = By.id("boton-ir-login");

    private final By alertaError = By.id("alerta-error");
    private final By alertaExito = By.id("alerta-exito");

    public RegisterRetailPage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
    }

    public void registrarUsuario(String usuario, String clave, String confirmarClave) {
        WebElement usuarioInput = wait.until(ExpectedConditions.visibilityOfElementLocated(inputUsuario));
        WebElement claveInput = wait.until(ExpectedConditions.visibilityOfElementLocated(inputClave));
        WebElement confirmarClaveInput = wait.until(ExpectedConditions.visibilityOfElementLocated(inputConfirmarClave));
        WebElement registroButton = wait.until(ExpectedConditions.elementToBeClickable(botonRegistro));

        usuarioInput.clear();
        usuarioInput.sendKeys(usuario);

        claveInput.clear();
        claveInput.sendKeys(clave);

        confirmarClaveInput.clear();
        confirmarClaveInput.sendKeys(confirmarClave);

        registroButton.click();
    }

    public boolean estaVisibleAlertaError() {
        try {
            WebElement alerta = wait.until(ExpectedConditions.visibilityOfElementLocated(alertaError));
            return alerta.isDisplayed();
        } catch (Exception e) {
            return false;
        }
    }

    public boolean estaVisibleAlertaExito() {
        try {
            WebElement alerta = wait.until(ExpectedConditions.visibilityOfElementLocated(alertaExito));
            return alerta.isDisplayed();
        } catch (Exception e) {
            return false;
        }
    }

    public String obtenerMensajeDeError() {
        try {
            WebElement alerta = wait.until(ExpectedConditions.visibilityOfElementLocated(alertaError));
            return alerta.getText();
        } catch (Exception e) {
            return null;
        }
    }

    public String obtenerMensajeDeExito() {
        try {
            WebElement alerta = wait.until(ExpectedConditions.visibilityOfElementLocated(alertaExito));
            return alerta.getText();
        } catch (Exception e) {
            return null;
        }
    }

    public void irAlLoginDesdeRegistro() {
        WebElement botonLogin = wait.until(ExpectedConditions.elementToBeClickable(botonIrLogin));
        botonLogin.click();
    }
}
