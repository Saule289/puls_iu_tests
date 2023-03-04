package pages;

import com.codeborne.selenide.selector.ByText;

import static com.codeborne.selenide.CollectionCondition.texts;
import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Configuration.baseUrl;
import static com.codeborne.selenide.Selectors.byText;
import static com.codeborne.selenide.Selectors.withText;
import static com.codeborne.selenide.Selenide.*;

public class PageObjects {

    public PageObjects openPage() {
        open(baseUrl);
        return this;
    }

    public PageObjects choiceMenuHeaders(String value) {

        $(".header-nav").$(withText(value)).hover();

        return this;
    }

    public PageObjects clickOnPrivateIcon() {

        $(".icon-private").click();
        return this;
    }

    public PageObjects switchToNewWindow(int value) {

        switchTo().window(value);
        return this;
    }

}
