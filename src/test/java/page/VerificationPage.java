package page;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.SelenideElement;

import static com.codeborne.selenide.Selenide.$;

public class VerificationPage {

    private SelenideElement code = $("[data-test-id='code']");

    public boolean verify() {
        if (code.has(Condition.text("Код из SMS или Push")) && code.is(Condition.visible)) return true;
        else return false;

    }
}