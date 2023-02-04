package page;

import com.codeborne.selenide.SelenideElement;
import data.DataHelper;


import static com.codeborne.selenide.Selenide.$;

public class LoginPage {

    private SelenideElement loginField = $("[data-test-id='login'] input");
    private SelenideElement passwordField = $("[data-test-id='password'] input");
    private SelenideElement loginButton = $("[data-test-id='action-login']");

    public VerificationPage login(DataHelper.AuthInfo user) {
        loginField.val(user.getLogin());
        passwordField.val(user.getPassword());
        loginButton.click();
        return new VerificationPage();
    }
}