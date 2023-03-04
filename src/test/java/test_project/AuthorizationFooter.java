package test_project;

import com.codeborne.selenide.Selenide;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import test_project.TestBase;

import static io.qameta.allure.Allure.step;

public class AuthorizationFooter extends TestBase {
    @Tag("remote")
    @Test
    @DisplayName("Проверка элементов футера на странице авторизации")
    public void checkFooterElements() {


        step("Открываем главную страницу", () -> {

            pageObjects.openPage();
        });

        step("Клик на иконку Личный кабинет", () -> {

            pageObjects.clickOnPrivateIcon();
        });

        step("Переход на новую вкладку формы авторизации", () -> {

            pageObjects.switchToNewWindow(1);

        });

        step("Проверка элементов на левой стороне страницы", () -> {

            results.checkLeftFooterElements("© 2023 ООО «ФК ПУЛЬС»");
            results.checkLeftFooterElements("Тех. поддержка");
            results.checkLeftFooterElements("Контакты");
            results.checkLeftFooterElements("Нормативные документы");

        });

        step("Проверка элементов на правой стороное страницы", () -> {

            results.checkRightFooterElements("puls.ru");
            results.checkRightFooterElements("+7 (495) 665-76-20");
            results.checkRightFooterElements("puls@puls.ru");
            Selenide.closeWebDriver();

        });
    }
}
