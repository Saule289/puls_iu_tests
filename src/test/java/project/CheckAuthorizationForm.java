package project;

import com.codeborne.selenide.Selenide;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Selenide.$;
import static io.qameta.allure.Allure.step;

public class CheckAuthorizationForm extends TestBase {

    @Tag("remote")
    @Test
    @DisplayName("Проверка перехода на страницу авторизации")

    public void checkLoginForm() {

        step("Закрыть все вкладки", () -> {

            Selenide.closeWebDriver();
        });

        {
            step("Открываем главную страницу", () -> {

                pageObjects.openPage();
            });

            step("Клик на иконку Личный кабинет", () -> {

                pageObjects.clickOnPrivateIcon();
            });

            step("Переход на новую вкладку формы авторизации", () -> {

                pageObjects.switchToNewWindow(1);

            });

            step("Проверка название формы", () -> {

                $(".auth__title").shouldHave(text("Вход в личный кабинет"));
            });

            step("Проверка название полей формы авторизации", () -> {

                results.checkNamesOfInputsOnAuthorizationForm("Логин");
                results.checkNamesOfInputsOnAuthorizationForm("Пароль");
                results.checkNamesOfInputsOnAuthorizationForm("Региональная компания");
            });

            step("Проверка название кнопок", () -> {

                results.checkNamesOfButtonsOnAuthorizationForm("Регистрация");
                results.checkNamesOfButtonsOnAuthorizationForm("Войти");
                results.checkNamesOfButtonsOnAuthorizationForm("Забыли пароль?");
            });

        }
    }
}
