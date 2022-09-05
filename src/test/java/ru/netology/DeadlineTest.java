package ru.netology;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Selenide.*;

public class DeadlineTest {

    @BeforeEach
    void openPage() {
        open("http://localhost:9999");
        refresh();
    }

    @Test
    void test1() {
        var verificationPage = new VerificationPage();
        $("[data-test-id=error-notification]").shouldHave(text("Ошибка! Неверно указан логин или пароль"));
    }

    @Test
    void test2() {
        $("[data-test-id=login] input").setValue("vasya");
        $("[data-test-id=password] input").setValue("qwerty123");
        $("[data-test-id=action-login]").click();
        $("[data-test-id=code] input").setValue("12345");
        $("[data-test-id=action-verify]").click();
        $("[data-test-id=error-notification]").shouldHave(text("Ошибка! Неверно указан код! Попробуйте ещё раз."));
    }
}
