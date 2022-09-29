package ru.netology.data;

import lombok.Value;
import lombok.val;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.ScalarHandler;

import java.sql.DriverManager;
import java.sql.SQLException;

public class DataHelper {
    private DataHelper() {
    }

    @Value
    public static class AuthInfo {
        private String login;
        private String password;
    }

    public static AuthInfo getAuthInfo() {
        return new AuthInfo("vasya", "qwerty123");
    }

    public static AuthInfo getAuthInfoNotValid() {
        return new AuthInfo("vasyaaa", "qwerty123123");
    }

    @Value
    public static class VerificationCode {
        private String code;
    }

    public static VerificationCode getVerificationCode() {
        val verificationCodeSQL = "SELECT code FROM auth_codes ORDER BY created DESC LIMIT 1;";
        String verificationCode;
        val runner = new QueryRunner();

        try (
                val conn = DriverManager.getConnection(
                        "jdbc:mysql://localhost:3306/app", "lena", "pass"
                );
        ) {
            val code = runner.query(conn, verificationCodeSQL, new ScalarHandler<>());
            verificationCode = (String) code;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return new VerificationCode(verificationCode);
    }

    public static VerificationCode getCodeNotValid() {
        return new VerificationCode("12345");
    }

    public static void clearSQL() {
        val deleteAuthCodes = "DELETE FROM auth_codes;";
        val deleteCardTransactions = "DELETE FROM card_transactions;";
        val deleteCards = "DELETE FROM cards;";
        val deleteUsers = "DELETE FROM users;";
        val runner = new QueryRunner();

        try (
                val conn = DriverManager.getConnection(
                        "jdbc:mysql://localhost:3306/app", "lena", "pass"
                );
        ) {
            runner.update(conn, deleteAuthCodes);
            runner.update(conn, deleteCardTransactions);
            runner.update(conn, deleteCards);
            runner.update(conn, deleteUsers);

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}