package test_project;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.Selenide;
import com.codeborne.selenide.SelenideElement;
import com.codeborne.selenide.selector.ByText;
import com.codeborne.selenide.selector.WithText;
import com.github.javafaker.Faker;
import data.City;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.ValueSource;

import java.util.stream.Stream;

import static com.codeborne.selenide.Condition.*;
import static com.codeborne.selenide.Selectors.byText;
import static com.codeborne.selenide.Selectors.withText;
import static com.codeborne.selenide.Selenide.*;
import static io.qameta.allure.Allure.step;


@Tag("remote")
public class Ui_tests extends TestBase {

    @Test
    @DisplayName("Проверяем лого компании и название вкладок меню")

    public void checkHeaderNames() {

        step("Открываем главную страницу", () -> {

            pageObjects.openPage();
        });

        step("Проверка лого компании", () -> {

            results.checkLogoName("Вместе мы делаем\n" +
                    "лекарства доступными");
        });

        step("Проверка названия первого элемента меню", () -> {
            results.checkNamesOfHeaders("О компании");
        });
        step("Проверка названия второго элемента меню", () -> {
            results.checkNamesOfHeaders("Группа компаний");
        });
        step("Проверка названия третьего элемента меню", () -> {
            results.checkNamesOfHeaders("Поставщикам");
        });
        step("Проверка названия четвертого элемента меню", () -> {
            results.checkNamesOfHeaders("Соискателям");
        });
        step("Проверка названия пятого элемента меню", () -> {
            results.checkNamesOfHeaders("Контакты");
        });
        step("Проверка названия шестого элемента меню", () -> {
            results.checkNamesOfHeaders("Тендер");
        });
        step("Проверка названия седьмого элемента меню", () -> {
            results.checkNamesOfHeaders("Маркировка");
        });
    }


    @Test
    @DisplayName("При смене языка меняется лого компании на английский")
    void logoChangedToEnglish() {

        step("Открываем главную страницу", () -> {
            pageObjects.openPage();
        });

        step("Кликнуть на ссылку Русский язык для смены языка на английский", () -> {
            $("i.icon-english").click();
        });

        step("Проверить, что лого отображено на английском языке", () -> {
            results.checkLogoName("Together we make medicines accessible");
        });
    }

    @ParameterizedTest
    @DisplayName("В выдаче поиска присутсвутет введенное название")
    @ValueSource(strings = {
            "Доставка", "Личный кабинет", "Брянск", "Маркировка"
    })
    void searchCheck(String searchInput) {

        step("Открываем главную страницу", () -> {
            pageObjects.openPage();
        });

        step("Тап на строку поиска и ввод элемента для посика", () -> {
            $(".hn-input").hover().setValue(searchInput).pressEnter();
        });

        step("Первая строка в выдаче поиска содержит запрашиваемую информацию", () -> {
            $("p").shouldHave(text(searchInput));
        });
    }

    static Stream<Arguments> contactOfCity() {
        return Stream.of(
                Arguments.of(City.Брянск, "+7 4832 36 70 00"),
                Arguments.of(City.Воронеж, "+7 473 233 35 47"),
                Arguments.of(City.Волгоград, "+7 844 251 08 51"),
                Arguments.of(City.Екатеринбург, "+7 343 287 39 35"),
                Arguments.of(City.Иркутск, "+7 395 256 74 41")
        );
    }

    @MethodSource("contactOfCity")
    @ParameterizedTest
    @DisplayName("Проверка номеров телефона для города {0}")
    void chosenCityReflectedInTheRightCornerOfPage(
            City city,
            String contact_number

    ) {
        step("Открываем главную страницу", () -> {
            pageObjects.openPage();
        });

        step("Навести мышку на вкладку Группа Компаний", () -> {
            pageObjects.choiceMenuHeaders("Группа компаний");
        });

        step("Выбрать город", () -> {
            $$(".sm-row").find(text(city.name())).click();
        });

        step("Проверить соответствие телефона выбранному городу", () -> {
            $(".is-link").shouldHave(text(contact_number));
        });
    }

    @CsvSource({
            "Поставщикам, Контакты для поставщиков",
            "Клиентам, Контакты для клиентов",
            "Соискателям, Контакты "

    })

    @ParameterizedTest
    @DisplayName("Проверка названия ссылок контактов для отдельных групп")
    void searchInformation
            (

                    String divisions,
                    String contacts_reference
            ) {
        step("Открываем главную страницу", () -> {
            pageObjects.openPage();
        });

        step("Навести мышку на вкладку" + " " + divisions, () -> {
            $(byText(divisions)).hover();
        });

        step("Кликнуть по" + " " + contacts_reference, () -> {
            $(byText(contacts_reference)).click();
        });

        step("Проверка название ссылки для просмотра контактов" + " " + contacts_reference + " " + "по выбранной вкладке" + " " + divisions, () -> {
            $(".pd-content").shouldHave(text(contacts_reference));
        });
    }

    @Test
    @DisplayName("Проверка перехода на страницу авторизации")

    public void checkLoginForm() {

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
    }

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

        });
    }

}
