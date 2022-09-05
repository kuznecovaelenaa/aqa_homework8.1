package ru.netology;
import com.github.javafaker.Faker;

import java.util.Locale;

import static com.codeborne.selenide.Selenide.$;

public class VerificationPage {

    private static final Faker faker = new Faker(new Locale("en"));

    public VerificationPage() {
        $("[data-test-id=login] input").setValue(faker.name().firstName());
        $("[data-test-id=password] input").setValue(faker.internet().password());
        $("[data-test-id=action-login]").click();

    }
}
