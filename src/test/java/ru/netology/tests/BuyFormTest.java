package ru.netology.tests;

import com.codeborne.selenide.logevents.SelenideLogger;
import io.qameta.allure.selenide.AllureSelenide;
import org.junit.jupiter.api.*;
import ru.netology.data.DBHelper;
import ru.netology.data.DataHelper;
import ru.netology.pages.PaymentMethod;

import static com.codeborne.selenide.Selenide.open;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class BuyFormTest {
    public static String url = System.getProperty("sut.url");

    @BeforeEach
    public void openPage() {
        open(url);
    }

    @AfterEach
    public void cleanBase() {
        DBHelper.clearDB();
    }

    @BeforeAll
    static void setUpAll() {
        SelenideLogger.addListener("allure", new AllureSelenide());
    }

    @AfterAll
    static void tearDownAll() {
        SelenideLogger.removeListener("allure");
    }

    @Test
    void buyPositiveAllFieldValidApproved() {
        var startPage = new PaymentMethod();
        var payment = startPage.goToBuyPage();
        payment.inputData(DataHelper.getApprovedCard());
        payment.waitNotificationApproved();
        assertEquals("APPROVED", DBHelper.getPaymentStatus());
    }

    @Test
    void buyPositiveAllFieldValidDeclined() {
        var startPage = new PaymentMethod();
        var payment = startPage.goToBuyPage();
        payment.inputData(DataHelper.getDeclinedCard());
        payment.waitNotificationFailure();
        assertEquals("DECLINED", DBHelper.getPaymentStatus());
    }

    @Test
    void buyNegativeAllFieldEmpty() {
        var startPage = new PaymentMethod();
        var payment = startPage.goToBuyPage();
        payment.inputData(DataHelper.getEmptyCard());
        payment.waitNotificationWrongFormat4Fields();
        assertEquals("0", DBHelper.getOrderCount());
    }

    @Test
    void buyNegativeNumberCard15Symbols() {
        var startPage = new PaymentMethod();
        var payment = startPage.goToBuyPage();
        payment.inputData(DataHelper.getNumberCard15Symbols());
        payment.waitNotificationWrongFormat();
        assertEquals("0", DBHelper.getOrderCount());
    }

    @Test
    void buyNegativeCardNotInDatabase() {
        var startPage = new PaymentMethod();
        var payment = startPage.goToBuyPage();
        payment.inputData(DataHelper.getCardNotInDatabase());
        payment.waitNotificationFailure();
        assertEquals("0", DBHelper.getOrderCount());
    }

    @Test
    void buyNegativeMonth1Symbol() {
        var startPage = new PaymentMethod();
        var payment = startPage.goToBuyPage();
        payment.inputData(DataHelper.getCardMonth1Symbol());
        payment.waitNotificationWrongFormat();
        assertEquals("0", DBHelper.getOrderCount());
    }

    @Test
    void buyNegativeMonthOver12() {
        var startPage = new PaymentMethod();
        var payment = startPage.goToBuyPage();
        payment.inputData(DataHelper.getCardMonthOver12());
        payment.waitNotificationExpirationDateError();
        assertEquals("0", DBHelper.getOrderCount());
    }

    @Test
    void buyNegativeMonth00ThisYear() {
        var startPage = new PaymentMethod();
        var payment = startPage.goToBuyPage();
        payment.inputData(DataHelper.getCardMonth00ThisYear());
        payment.waitNotificationExpirationDateError();
        assertEquals("0", DBHelper.getOrderCount());
    }

    @Test
    void buyNegativeMonth00OverThisYear() {
        var startPage = new PaymentMethod();
        var payment = startPage.goToBuyPage();
        payment.inputData(DataHelper.getCardMonth00OverThisYear());
        payment.waitNotificationExpirationDateError();
        assertEquals("0", DBHelper.getOrderCount());
    }

    @Test
    void buyNegativeYear00() {
        var startPage = new PaymentMethod();
        var payment = startPage.goToBuyPage();
        payment.inputData(DataHelper.getCardYear00());
        payment.waitNotificationExpiredError();
        assertEquals("0", DBHelper.getOrderCount());
    }

    @Test
    void buyNegativeYear1Symbol() {
        var startPage = new PaymentMethod();
        var payment = startPage.goToBuyPage();
        payment.inputData(DataHelper.getCardYear1Symbol());
        payment.waitNotificationWrongFormat();
        assertEquals("0", DBHelper.getOrderCount());
    }

    @Test
    void buyNegativeYearUnderThisYear() {
        var startPage = new PaymentMethod();
        var payment = startPage.goToBuyPage();
        payment.inputData(DataHelper.getCardYearUnderThisYear());
        payment.waitNotificationExpiredError();
        assertEquals("0", DBHelper.getOrderCount());
    }

    @Test
    void buyNegativeYearOverThisYearOn6() {
        var startPage = new PaymentMethod();
        var payment = startPage.goToBuyPage();
        payment.inputData(DataHelper.getCardYearOverThisYearOn6());
        payment.waitNotificationExpirationDateError();
        assertEquals("0", DBHelper.getOrderCount());
    }

    @Test
    void buyNegativeCvv1Symbol() {
        var startPage = new PaymentMethod();
        var payment = startPage.goToBuyPage();
        payment.inputData(DataHelper.getCardCvv1Symbol());
        payment.waitNotificationWrongFormat();
        assertEquals("0", DBHelper.getOrderCount());
    }

    @Test
    void buyNegativeCvv2Symbols() {
        var startPage = new PaymentMethod();
        var payment = startPage.goToBuyPage();
        payment.inputData(DataHelper.getCardCvv2Symbols());
        payment.waitNotificationWrongFormat();
        assertEquals("0", DBHelper.getOrderCount());
    }

    @Test
    void buyNegativeOwner1Word() {
        var startPage = new PaymentMethod();
        var payment = startPage.goToBuyPage();
        payment.inputData(DataHelper.getCardHolder1Word());
        payment.waitNotificationWrongFormat();
        assertEquals("0", DBHelper.getOrderCount());
    }

    @Test
    void buyNegativeOwnerCirillic() {
        var startPage = new PaymentMethod();
        var payment = startPage.goToBuyPage();
        payment.inputData(DataHelper.getCardHolderCirillic());
        payment.waitNotificationWrongFormat();
        assertEquals("0", DBHelper.getOrderCount());
    }

    @Test
    void buyNegativeOwnerNumeric() {
        var startPage = new PaymentMethod();
        var payment = startPage.goToBuyPage();
        payment.inputData(DataHelper.getCardHolderNumeric());
        payment.waitNotificationWrongFormat();
        assertEquals("0", DBHelper.getOrderCount());
    }

    @Test
    void buyNegativeOwnerSpecialSymbols() {
        var startPage = new PaymentMethod();
        var payment = startPage.goToBuyPage();
        payment.inputData(DataHelper.getCardSpecialSymbols());
        payment.waitNotificationWrongFormat();
        assertEquals("0", DBHelper.getOrderCount());
    }
}