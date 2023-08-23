package ru.netology.tests;

import com.codeborne.selenide.logevents.SelenideLogger;
import io.qameta.allure.selenide.AllureSelenide;
import lombok.val;
import org.junit.jupiter.api.*;
import ru.netology.data.DBHelper;
import ru.netology.data.DataHelper;
import ru.netology.pages.PaymentMethod;

import static com.codeborne.selenide.Selenide.open;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class BuyFormTest {

    @BeforeEach
    public void openPage() {
        open("http://localhost:8080/");
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
        val startPage = new PaymentMethod();
        val payment = startPage.goToBuyPage();
        payment.inputData(DataHelper.getApprovedCard());
        payment.waitNotificationApproved();
        assertEquals("APPROVED", DBHelper.getPaymentStatus());
    }

    @Test
    void buyPositiveAllFieldValidDeclined() {
        val startPage = new PaymentMethod();
        val payment = startPage.goToBuyPage();
        payment.inputData(DataHelper.getDeclinedCard());
        payment.waitNotificationFailure();
        assertEquals("DECLINED", DBHelper.getPaymentStatus());
    }

    @Test
    void buyNegativeAllFieldEmpty() {
        val startPage = new PaymentMethod();
        val payment = startPage.goToBuyPage();
        payment.inputData(DataHelper.getEmptyCard());
        payment.waitNotificationWrongFormat4Fields();
        assertEquals("0", DBHelper.getOrderCount());
    }

    @Test
    void buyNegativeNumberCard15Symbols() {
        val startPage = new PaymentMethod();
        val payment = startPage.goToBuyPage();
        payment.inputData(DataHelper.getNumberCard15Symbols());
        payment.waitNotificationWrongFormat();
        assertEquals("0", DBHelper.getOrderCount());
    }

    @Test
    void buyNegativeCardNotInDatabase() {
        val startPage = new PaymentMethod();
        val payment = startPage.goToBuyPage();
        payment.inputData(DataHelper.getCardNotInDatabase());
        payment.waitNotificationFailure();
        assertEquals("0", DBHelper.getOrderCount());
    }

    @Test
    void buyNegativeMonth1Symbol() {
        val startPage = new PaymentMethod();
        val payment = startPage.goToBuyPage();
        payment.inputData(DataHelper.getCardMonth1Symbol());
        payment.waitNotificationWrongFormat();
        assertEquals("0", DBHelper.getOrderCount());
    }

    @Test
    void buyNegativeMonthOver12() {
        val startPage = new PaymentMethod();
        val payment = startPage.goToBuyPage();
        payment.inputData(DataHelper.getCardMonthOver12());
        payment.waitNotificationExpirationDateError();
        assertEquals("0", DBHelper.getOrderCount());
    }

    @Test
    void buyNegativeMonth00ThisYear() {
        val startPage = new PaymentMethod();
        val payment = startPage.goToBuyPage();
        payment.inputData(DataHelper.getCardMonth00ThisYear());
        payment.waitNotificationExpirationDateError();
        assertEquals("0", DBHelper.getOrderCount());
    }

    @Test
    void buyNegativeMonth00OverThisYear() {
        val startPage = new PaymentMethod();
        val payment = startPage.goToBuyPage();
        payment.inputData(DataHelper.getCardMonth00OverThisYear());
        payment.waitNotificationExpirationDateError();
        assertEquals("0", DBHelper.getOrderCount());
    }

    @Test
    void buyNegativeYear00() {
        val startPage = new PaymentMethod();
        val payment = startPage.goToBuyPage();
        payment.inputData(DataHelper.getCardYear00());
        payment.waitNotificationExpiredError();
        assertEquals("0", DBHelper.getOrderCount());
    }

    @Test
    void buyNegativeYear1Symbol() {
        val startPage = new PaymentMethod();
        val payment = startPage.goToBuyPage();
        payment.inputData(DataHelper.getCardYear1Symbol());
        payment.waitNotificationWrongFormat();
        assertEquals("0", DBHelper.getOrderCount());
    }

    @Test
    void buyNegativeYearUnderThisYear() {
        val startPage = new PaymentMethod();
        val payment = startPage.goToBuyPage();
        payment.inputData(DataHelper.getCardYearUnderThisYear());
        payment.waitNotificationExpiredError();
        assertEquals("0", DBHelper.getOrderCount());
    }

    @Test
    void buyNegativeYearOverThisYearOn6() {
        val startPage = new PaymentMethod();
        val payment = startPage.goToBuyPage();
        payment.inputData(DataHelper.getCardYearOverThisYearOn6());
        payment.waitNotificationExpirationDateError();
        assertEquals("0", DBHelper.getOrderCount());
    }

    @Test
    void buyNegativeCvv1Symbol() {
        val startPage = new PaymentMethod();
        val payment = startPage.goToBuyPage();
        payment.inputData(DataHelper.getCardCvv1Symbol());
        payment.waitNotificationWrongFormat();
        assertEquals("0", DBHelper.getOrderCount());
    }

    @Test
    void buyNegativeCvv2Symbols() {
        val startPage = new PaymentMethod();
        val payment = startPage.goToBuyPage();
        payment.inputData(DataHelper.getCardCvv2Symbols());
        payment.waitNotificationWrongFormat();
        assertEquals("0", DBHelper.getOrderCount());
    }

    @Test
    void buyNegativeOwner1Word() {
        val startPage = new PaymentMethod();
        val payment = startPage.goToBuyPage();
        payment.inputData(DataHelper.getCardHolder1Word());
        payment.waitNotificationWrongFormat();
        assertEquals("0", DBHelper.getOrderCount());
    }

    @Test
    void buyNegativeOwnerCirillic() {
        val startPage = new PaymentMethod();
        val payment = startPage.goToBuyPage();
        payment.inputData(DataHelper.getCardHolderCirillic());
        payment.waitNotificationWrongFormat();
        assertEquals("0", DBHelper.getOrderCount());
    }

    @Test
    void buyNegativeOwnerNumeric() {
        val startPage = new PaymentMethod();
        val payment = startPage.goToBuyPage();
        payment.inputData(DataHelper.getCardHolderNumeric());
        payment.waitNotificationWrongFormat();
        assertEquals("0", DBHelper.getOrderCount());
    }

    @Test
    void buyNegativeOwnerSpecialSymbols() {
        val startPage = new PaymentMethod();
        val payment = startPage.goToBuyPage();
        payment.inputData(DataHelper.getCardSpecialSymbols());
        payment.waitNotificationWrongFormat();
        assertEquals("0", DBHelper.getOrderCount());
    }
}