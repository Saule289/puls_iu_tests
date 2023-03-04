package test_project;

import com.codeborne.selenide.Selenide;
import com.github.javafaker.Faker;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import test_project.TestBase;

import static com.codeborne.selenide.Selectors.byText;
import static com.codeborne.selenide.Selectors.withText;
import static com.codeborne.selenide.Selenide.$;
import static io.qameta.allure.Allure.step;

public class InvalidAuthorization extends TestBase {

    @Tag("remote")
    @Test
    @DisplayName("Авторизация незарегистрированного пользователя")
    public void authorizationByUnregisteredClient() {
        Faker faker = new Faker();
        String cityRepresentation = faker.options().option("ПУЛЬС Волгоград", "ПУЛЬС Воронеж");


        step("Открываем главную страницу", () -> {

            pageObjects.openPage();
        });

        step("Клик на иконку Личный кабинет", () -> {

            pageObjects.clickOnPrivateIcon();
        });

        step("Переход на новую вкладку формы авторизации", () -> {

            pageObjects.switchToNewWindow(1);

        });

        step("Ввести логин", () -> {
            $("[name=username]").setValue(faker.name().username());
        });

        step("Ввести пароль", () -> {
            $("[name=password]").setValue(faker.internet().password());
        });

        step("Выьрать региональную компанию", () -> {
            $(".vs__open-indicator").click();
            $(byText(cityRepresentation)).click();
        });

        step("Нажать на нопку Войти", () -> {
            $(".auth__submit").click();
        });

        step("Появляется модальное окно с сообщение об ошибке аутентификации", () -> {

            $(".modal__message-title").$(withText("Ошибка аутентификации")).isDisplayed();
        });
        Selenide.closeWebDriver();
    }
}
