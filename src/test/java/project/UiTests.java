package project;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.Selenide;
import data.City;
import io.qameta.allure.Feature;
import io.qameta.allure.Layer;
import io.qameta.allure.Owner;
import org.junit.jupiter.api.Disabled;
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
import static data.City.*;
import static io.qameta.allure.Allure.step;


@Owner("saulezhan")
@Feature("Issues")
@Layer("web")
@Tag("remote")
public class UiTests extends TestBase {

    @Test
    @DisplayName("Проверяем лого компании и название вкладок меню")

    public void checkHeaderNames() {

        step("Закрыть все вкладки", () -> {

            Selenide.closeWebDriver();
        });

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

        step("Закрыть все вкладки", () -> {

            Selenide.closeWebDriver();
        });

        step("Открываем главную страницу", () -> {
            pageObjects.openPage();
        });

        step("Кликнуть на ссылку Русский язык для смены языка на английский", () -> {
            $(".lang-select__select").click();
            $(".select__options").$$("a").get(0).click();
        });

        step("Проверить, что лого отображено на английском языке", () -> {
            results.checkLogoName("\n" +
                    "            National Pharmaceutical Distributor \n" +
                    "        ");
        });
    }

    @ParameterizedTest
    @DisplayName("В выдаче поиска присутсвутет введенное название")
    @ValueSource(strings = {
            "Дистрибьютор", "Завод", "Фармацевт", "Оборудование"
    })
    void searchCheck(String searchInput) {

        step("Закрыть все вкладки", () -> {

            Selenide.closeWebDriver();
        });

        step("Открываем главную страницу", () -> {
            pageObjects.openPage();
        });

        step("Тап на строку поиска и ввод элемента для посика", () -> {
            $(".search-btn__open").click();
            $(".fs-20").click();
            $(".fs-20").setValue(searchInput).pressEnter();
        });

        step("Первая строка в выдаче поиска содержит запрашиваемую информацию", () -> {
            $(".info__title").shouldHave(text(searchInput));
        });
    }

    static Stream<Arguments> contactOfCity() {
        return Stream.of(
                Arguments.of(BRYANSK, "+7 4832 36 70 00"),
                Arguments.of(VORONEZH, "+7 473 233 35 47"),
                Arguments.of(VOLGOGRAD, "+7 844 251 08 51"),
                Arguments.of(EKATERINBURG, "+7 343 287 39 35"),
                Arguments.of(IRKUTSK, "+7 395 256 74 41")
        );
    }

    @MethodSource("contactOfCity")
    @ParameterizedTest
    @DisplayName("Проверка номеров телефона для города {0}")
    void chosenCityReflectedInTheRightCornerOfPage(
            City city,
            String contact_number

    ) {
        step("Закрыть все вкладки", () -> {

            Selenide.closeWebDriver();
        });

        step("Открываем главную страницу", () -> {
            pageObjects.openPage();
        });

        step("Навести мышку на вкладку О нас", () -> {
            pageObjects.choiceMenuHeaders("О нас");
        });

        step("Выбрать категорию Группа компания", () -> {
            $(".nav__inner-list").$(withText("Группа компаний")).hover();
        });
        step("Выбрать город", () -> {
            $$(".fs-16").findBy(Condition.text(city.getDesc())).click();;
        });

        step("Проверить соответствие телефона выбранному городу", () -> {
            $$(".about__info").get(1).shouldHave(text(contact_number));
        });
    }

    @CsvSource({
            "Поставщикам, Контакты для поставщиков",
            "Клиентам, Контакты для клиентов",
            "МС «СОЗВЕЗДИЕ», Контакты "

    })
      @Disabled("переделали страницу")
    @ParameterizedTest
    @DisplayName("Проверка названия ссылок контактов для отдельных групп")
    void searchInformation
            (

                    String divisions,
                    String contactsReference
            ) {



        step("Открываем главную страницу", () -> {
            pageObjects.openPage();
        });


        step("Навести мышку на вкладку Сотрудничество", () -> {
            pageObjects.choiceMenuHeaders("Сотрудничество");
        });


        step("Навести мышку на вкладку" + " " + divisions, () -> {
            $(".nav__inner-list").$(withText(divisions)).click();
        });

        step("Кликнуть по" + " " + contactsReference, () -> {
            $(byText(contactsReference)).click();
        });

        step("Проверка название ссылки для просмотра контактов" + " " + contactsReference + " " + "по выбранной вкладке" + " " + divisions, () -> {
            $(".pd-content").shouldHave(text(contactsReference));
        });

    }
}
