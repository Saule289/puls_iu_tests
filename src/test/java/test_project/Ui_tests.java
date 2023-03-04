package test_project;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.selector.ByText;
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

import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selectors.byText;
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

    @ParameterizedTest(name = "В выдаче поиска присутсвутет введенное название")
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
    @ParameterizedTest(name = "Проверка номеров телефона для города {0}")
    void chosenCityReflectedInTheRightCornerOfPage(
            City city,
            String contact_number

    ) {
        step("Открываем главную страницу", () -> {
            pageObjects.openPage();
        });

        step("Навести мышку на вкладку Группа Компаний", () -> {
            pageObjects.choiceMenuHeaders( "Группа компаний");
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

    @ParameterizedTest(name = "Проверка названия ссылок контактов для отдельных групп")
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

}
