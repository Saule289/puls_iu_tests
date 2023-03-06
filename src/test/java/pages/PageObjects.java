package pages;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.SelenideElement;
import static com.codeborne.selenide.Configuration.baseUrl;

import static com.codeborne.selenide.Selectors.withText;
import static com.codeborne.selenide.Selenide.*;

public class PageObjects {

    public SelenideElement registration = $(".auth__reg");
    public SelenideElement forgetPassword = $(".auth__forget");

    public PageObjects openPage() {
        open(baseUrl);
        return this;
    }

    public PageObjects choiceMenuHeaders(String value) {

       $$(".list__item ").findBy(Condition.text(value)).hover();

        return this;
    }

    public PageObjects clickOnPrivateIcon() {

        $(".btn-secondary").click();
        return this;
    }

    public PageObjects switchToNewWindow(int value) {

        switchTo().window(value);
        return this;
    }

    public PageObjects clickOnButtonsOnLoginForm() {

        registration.click();
        forgetPassword.click();

        return this;
    }

}
