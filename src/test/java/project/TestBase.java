package project;

import com.codeborne.selenide.Selenide;
import com.codeborne.selenide.logevents.SelenideLogger;
import components.Attach;
import config.WebDriverConfig;
import config.WebDriverProvider;

import io.qameta.allure.selenide.AllureSelenide;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;

import org.junit.jupiter.api.Tag;


import pages.PageObjects;
import pages.Results;

import static io.qameta.allure.Allure.step;


@Tag("remote_test")
    public class TestBase {

     PageObjects pageObjects = new PageObjects();
     Results results = new Results();
    private static WebDriverConfig config;


        @BeforeAll
        static void beforeAll() {

            WebDriverProvider provider = new WebDriverProvider();
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

    void closeWindow() {
        step("Закрыть все вкладки", () -> {

            Selenide.closeWebDriver();
        });
    }

    }

