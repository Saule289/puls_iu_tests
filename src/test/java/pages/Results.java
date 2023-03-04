package pages;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.collections.ItemWithText;
import org.checkerframework.common.value.qual.StringVal;

import static com.codeborne.selenide.CollectionCondition.itemWithText;
import static com.codeborne.selenide.CollectionCondition.texts;
import static com.codeborne.selenide.Condition.cssClass;
import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.$$;

public class Results {

    public void checkLogoName(String value) {

        $(".ht-details").shouldHave(Condition.text(value));
    }

    public void checkNamesOfHeaders(String value) {
        $$(".hn-nav-list").shouldHave(texts(value));
    }

    public void checkNamesOfInputsOnAuthorizationForm(String value) {

        $$(".auth__input-wrap").shouldHave(itemWithText(value));
    }
    public void checkNamesOfButtonsOnAuthorizationForm(String value) {

       $$("button").shouldHave(itemWithText(value));
    }

}
