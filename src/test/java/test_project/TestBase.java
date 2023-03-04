package test_project;

import com.codeborne.selenide.Configuration;
import com.codeborne.selenide.logevents.SelenideLogger;
import components.Attach;
import io.qameta.allure.selenide.AllureSelenide;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.openqa.selenium.remote.DesiredCapabilities;
import pages.PageObjects;
import pages.Results;

import java.util.Map;

@Tag("remote_test")
    public class TestBase {

     PageObjects pageObjects = new PageObjects();
     Results results = new Results();

        @BeforeAll
        static void beforeAll() {

            Configuration.baseUrl = "https://puls.ru/";
            Configuration.browser = System.getProperty("browser", "chrome");
       Configuration.browserVersion = System.getProperty("browserVersion", "100.0");
            Configuration.browserSize = System.getProperty("browserSize", "1920x1080");
           Configuration.remote = System.getProperty("remote", "https://user1:1234@selenoid.autotests.cloud/wd/hub");


            DesiredCapabilities capabilities = new DesiredCapabilities();
            capabilities.setCapability("selenoid:options", Map.<String, Object>of(
                    "enableVNC", true,
                    "enableVideo", true
            ));

            Configuration.browserCapabilities = capabilities;
        }

        @BeforeEach
        void addListener() {
            SelenideLogger.addListener("AllureSelenide", new AllureSelenide());
        }

        @AfterEach
        void addAttachments() {
            Attach.screenshotAs("Last screenshot");
            Attach.pageSource();
            Attach.browserConsoleLogs();
            Attach.addVideo();
        }

    }

