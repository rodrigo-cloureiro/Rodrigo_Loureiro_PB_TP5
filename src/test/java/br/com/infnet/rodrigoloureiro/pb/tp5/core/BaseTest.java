package br.com.infnet.rodrigoloureiro.pb.tp5.core;

import io.github.bonigarcia.wdm.WebDriverManager;
import java.net.MalformedURLException;
import java.net.URI;
import lombok.*;
import org.junit.jupiter.api.*;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.remote.RemoteWebDriver;

@Getter
public class BaseTest {
    protected WebDriver driver;

    @BeforeEach
    public void setUp() throws MalformedURLException {
        WebDriverManager.chromedriver().setup();

        ChromeOptions options = new ChromeOptions()
                .addArguments("--incognito")
                .addArguments("--headless=new")
                .addArguments("--no-sandbox")
                .addArguments("--disable-dev-shm-usage")
                .addArguments("--disable-gpu");

        // this.driver = new ChromeDriver(options);
        // URI seleniumHubUri = URI.create("http://localhost:4444/wd/hub");
        URI seleniumHubUri = URI.create("http://selenium:4444/wd/hub");
        this.driver = new RemoteWebDriver(seleniumHubUri.toURL(), options);
    }

    @AfterEach
    public void tearDown() {
        if (this.driver != null) {
            this.driver.quit();
        }
    }
}
