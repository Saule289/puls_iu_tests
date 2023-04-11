package project;

import com.codeborne.selenide.Selenide;
import io.qameta.allure.Feature;
import io.qameta.allure.Layer;
import io.qameta.allure.Owner;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import static io.qameta.allure.Allure.step;

public class CheckAuthorizationButtons extends TestBase{
    @Owner("saulezhan")
    @Feature("Issues")
    @Layer("web")
    @Tag("remote")
    @Test
    @DisplayName("Проверка названия модального окна при нажатии на соответствующие кнопки")
    public void checkAuthorizationButtons() {

        step("Закрыть все вкладки", () -> {

            Selenide.closeWebDriver();
        });

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
            pageObjects.registration.click();
            results.checkNamesOfModalWindowOnAuthorizationForm("Регистрация аккаунта");
        });

        step("Проверка названия модального окна при нажатии на кнопку Забыли пароль", () -> {
            Selenide.refresh();
            pageObjects.forgetPassword.click();
            results.checkNamesOfModalWindowOnAuthorizationForm("Восстановление пароля");
        });
    }
}
