package data;

import lombok.SneakyThrows;
import lombok.Value;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;

import java.sql.DriverManager;

public class DataHelper {
    private DataHelper() {}

    @Value
    public static class AuthInfo {
        private String login;
        private String password;
    }

    @SneakyThrows
    public static AuthInfo getAuthInfo() {
        var usersSQL = "SELECT * FROM users where login='vasya';";
        var runner = new QueryRunner();
        try (
                var conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/app-db", "user", "pass")) {
            var first = runner.query(conn, usersSQL, new BeanHandler<>(User.class));
            first.setPassword("qwerty123");
        return new AuthInfo(first.getLogin(), first.getPassword());}
    }

}