package ru.netology.test;

import lombok.val;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.netology.data.DataHelper;
import ru.netology.page.DashboardPage;
import ru.netology.page.LoginPage;
import ru.netology.page.VerificationPage;

import static com.codeborne.selenide.Selenide.*;

public class DeadlineTest {

    @BeforeEach
    void openPage() {
        open("http://localhost:9999");
    }

    @AfterAll
    static void clearSQL() {
        DataHelper.clearSQL();
    }

    @Test
    void shouldLoginRegisteredUser() {
        var loginPage = new LoginPage();
        var authInfo = DataHelper.getAuthInfo();
        loginPage.validLogin(authInfo);
        var verificationPage = new VerificationPage();
        verificationPage.validVerify(DataHelper.getVerificationCode());
        val dashboardPage = new DashboardPage();
    }

    @Test
    void notShouldLoginNotRegisteredUser() {
        var loginPage = new LoginPage();
        var authInfo = DataHelper.getAuthInfoNotValid();
        loginPage.validLogin(authInfo);
        loginPage.getLoginNotification();
    }

    @Test
    void notShouldNotValidVerificationCode() {
        var loginPage = new LoginPage();
        var authInfo = DataHelper.getAuthInfo();
        loginPage.validLogin(authInfo);
        var verificationPage = new VerificationPage();
        verificationPage.validVerify(DataHelper.getCodeNotValid());
        verificationPage.getVerificationNotification();
    }

    @Test
    void shouldBlockedVerificationPage() {
        var loginPage = new LoginPage();
        var authInfo = DataHelper.getAuthInfo();
        loginPage.validLogin(authInfo);
        var verificationPage = new VerificationPage();
        verificationPage.validVerify(DataHelper.getCodeNotValid());
        openPage();
        loginPage.validLogin(authInfo);
        verificationPage.validVerify(DataHelper.getCodeNotValid());
        openPage();
        loginPage.validLogin(authInfo);
        verificationPage.validVerify(DataHelper.getCodeNotValid());
        openPage();
        loginPage.validLogin(authInfo);
        verificationPage.validVerify(DataHelper.getCodeNotValid());
        verificationPage.getBlockedVerificationNotification();
    }
}
