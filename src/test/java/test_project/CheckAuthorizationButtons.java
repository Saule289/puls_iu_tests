package test_project;

import com.codeborne.selenide.Selenide;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import test_project.TestBase;

import static io.qameta.allure.Allure.step;

public class CheckAuthorizationButtons extends TestBase{
    @Tag("remote")
    @Test
    @DisplayName("Проверка названия модального окна при нажатии на соответствующие кнопки")
    public void checkAuthorizationButtons() {

        step("Открываем главную страницу", () -> {

            pageObjects.openPage();
        });

        step("Клик на иконку Личный кабинет", () -> {

            pageObjects.clickOnPrivateIcon();
        });

        step("Переход на новую вкладку формы авторизации", () -> {

            pageObjects.switchToNewWindow(1);

        });

        step("Проверка названия модального окна при нажатии на кнопку Регистрации", () -> {
            pageObjects.clickOnButtonsOnLoginForm().registration.click();
            results.checkNamesOfModalWindowOnAuthorizationForm("Регистрация аккаунта");
        });

        step("Проверка названия модального окна при нажатии на кнопку Забыли пароль", () -> {
            Selenide.refresh();
            pageObjects.clickOnButtonsOnLoginForm().forgetPassword.click();
            results.checkNamesOfModalWindowOnAuthorizationForm("Восстановление пароля");
        });
    }
}
