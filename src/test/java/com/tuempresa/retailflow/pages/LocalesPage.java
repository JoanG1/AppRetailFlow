package com.tuempresa.retailflow.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.List;

public class LocalesPage {

    private final WebDriver driver;
    private final WebDriverWait wait;

    // Selectores con IDs únicos
    private final By inputNombreLocal = By.id("input-nombre-local");
    private final By botonAgregarLocal = By.id("boton-agregar-local");
    private final By alertaMensaje = By.id("alerta-local-mensaje");
    private final By tarjetasLocales = By.cssSelector("div[id^='tarjeta-local-']");

    public LocalesPage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(15));
    }

    public void esperarFormularioVisible() {
        wait.until(ExpectedConditions.visibilityOfElementLocated(inputNombreLocal));
    }

    public void ingresarNombreLocal(String nombre) {
        WebElement input = wait.until(ExpectedConditions.elementToBeClickable(inputNombreLocal));
        input.click();
        input.clear();

        // Usamos Actions para simular escritura humana
        Actions actions = new Actions(driver);
        actions.moveToElement(input).click().sendKeys(nombre).perform();

        // Esperar a que el botón esté habilitado (enabled)
        wait.until(ExpectedConditions.elementToBeClickable(botonAgregarLocal));
    }

    public void clickAgregarLocal() {
        WebElement boton = wait.until(ExpectedConditions.elementToBeClickable(botonAgregarLocal));
        boton.click();
    }

    public String obtenerMensaje() {
        try {
            WebElement alerta = wait.until(ExpectedConditions.visibilityOfElementLocated(alertaMensaje));
            return alerta.getText();
        } catch (Exception e) {
            return null;
        }
    }

    public boolean existeLocalConNombre(String nombre) {
        List<WebElement> tarjetas = wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(tarjetasLocales));
        return tarjetas.stream()
                .anyMatch(tarjeta -> tarjeta.getText().toLowerCase().contains(nombre.toLowerCase()));
    }
}
