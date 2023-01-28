import com.codeborne.selenide.Condition;
import com.codeborne.selenide.SelenideElement;
import lombok.SneakyThrows;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.DriverManager;
import java.sql.SQLException;

import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.open;

public class SQLTest {


    @AfterAll
    @SneakyThrows
    public static void tearDown() {
        var codeClearSQL = "DELETE FROM auth_codes;";
        var cardsClearSQL = "DELETE FROM cards;";
        var usersClearSQL = "DELETE FROM users;";
        try (
                var conn = DriverManager
                        .getConnection("jdbc:mysql://localhost:3306/app-db", "user", "pass");
                var codeClearStmt = conn.prepareStatement(codeClearSQL);
                var cardsClearStmt = conn.prepareStatement(cardsClearSQL);
                var usersClearStmt = conn.prepareStatement(usersClearSQL)) {
            codeClearStmt.executeUpdate();
            cardsClearStmt.executeUpdate();
            usersClearStmt.executeUpdate();
        }
    }

    @BeforeEach
    public void setUp() throws SQLException {
        open("http://localhost:9999");
    }

    @Test
    @SneakyThrows
    public void test() {
        var usersSQL = "SELECT * FROM users where login='vasya';";
        var runner = new QueryRunner();
        SelenideElement loginField = $("[data-test-id='login'] input");
        SelenideElement passwordField = $("[data-test-id='password'] input");
        SelenideElement loginButton = $("[data-test-id='action-login']");
        try (
                var conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/app-db", "user", "pass")) {
            var first = runner.query(conn, usersSQL, new BeanHandler<>(User.class));
            loginField.val(first.getLogin());
            passwordField.val("qwerty123");
            loginButton.click();
            $("[data-test-id='code']")
                    .shouldHave(Condition.text("Код из SMS или Push"))
                    .shouldBe(Condition.visible);

        }
    }
}
