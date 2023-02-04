package test;

import data.DataHelper;
import lombok.SneakyThrows;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import page.LoginPage;
import page.VerificationPage;

import java.sql.DriverManager;
import java.sql.SQLException;

import static com.codeborne.selenide.Selenide.open;

public class SQLTest {


    @AfterAll
    @SneakyThrows
    public static void tearDown() {
        var transactionsClearSQL = "DELETE FROM card_transactions;";
        var codeClearSQL = "DELETE FROM auth_codes;";
        var cardsClearSQL = "DELETE FROM cards;";
        var usersClearSQL = "DELETE FROM users;";
        try (
                var conn = DriverManager
                        .getConnection("jdbc:mysql://localhost:3306/app-db", "user", "pass");
                var transactionsClearStmt = conn.prepareStatement(transactionsClearSQL);
                var codeClearStmt = conn.prepareStatement(codeClearSQL);
                var cardsClearStmt = conn.prepareStatement(cardsClearSQL);
                var usersClearStmt = conn.prepareStatement(usersClearSQL)) {
            transactionsClearStmt.executeUpdate();
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
    public void test() {
        var userInfo = DataHelper.getAuthInfo();
        LoginPage loginPage = new LoginPage();
        VerificationPage verificationPage = loginPage.login(userInfo);
        Assertions.assertTrue(verificationPage.verify());
    }
}
