package com.tuempresa.retailflow.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class LoginRetailPage {

    private final WebDriver driver;
    private final WebDriverWait wait;

    // Localizadores basados en los id y data-testid del componente React
    private final By inputUsuario = By.id("input-usuario");
    private final By inputClave = By.id("input-clave");
    private final By botonLogin = By.id("boton-login");

    private final By alertaError = By.cssSelector("[data-testid='alerta-error']");

    public LoginRetailPage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
    }

    public void iniciarSesion(String usuario, String clave) {
        WebElement usuarioInput = wait.until(ExpectedConditions.visibilityOfElementLocated(inputUsuario));
        WebElement claveInput = wait.until(ExpectedConditions.visibilityOfElementLocated(inputClave));
        WebElement loginButton = wait.until(ExpectedConditions.elementToBeClickable(botonLogin));

        usuarioInput.clear();
        usuarioInput.sendKeys(usuario);

        claveInput.clear();
        claveInput.sendKeys(clave);

        loginButton.click();
    }

    public boolean estaVisibleAlertaError() {
        try {
            WebElement alerta = wait.until(ExpectedConditions.visibilityOfElementLocated(alertaError));
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
}
