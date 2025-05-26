package com.tuempresa.retailflow.base;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;


import java.nio.file.Files;

public class BaseTest {
    protected WebDriver driver;


    //Prueba github commit

    @BeforeEach
    public void setUp() throws Exception {
        ChromeOptions options = new ChromeOptions();

        // Crea un directorio temporal para --user-data-dir
        String tempProfileDir = Files.createTempDirectory("chrome-profile").toString();
        options.addArguments("--user-data-dir=" + tempProfileDir);

        options.addArguments("--headless=new"); // Para entornos sin GUI (como GitHub Actions)
        options.addArguments("--disable-gpu");
        options.addArguments("--no-sandbox");
        options.addArguments("--disable-dev-shm-usage");

        driver = new ChromeDriver(options);
        driver.manage().window().maximize();
    }
    @AfterEach
    public void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }
}

