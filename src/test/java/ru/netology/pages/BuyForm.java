package ru.netology.pages;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.ElementsCollection;
import com.codeborne.selenide.SelenideElement;
import ru.netology.data.DataHelper;

import java.time.Duration;

import static com.codeborne.selenide.Condition.*;
import static com.codeborne.selenide.Selectors.byText;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.$$;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class BuyForm {
    private final SelenideElement cardNumberField = $(byText("Номер карты")).parent().$("[class=\"input__control\"]");
    private final SelenideElement monthField = $(byText("Месяц")).parent().$("[class=\"input__control\"]");
    private final SelenideElement yearField = $(byText("Год")).parent().$("[class=\"input__control\"]");
    private final SelenideElement cardHolderField = $(byText("Владелец")).parent().$("[class=\"input__control\"]");
    private final SelenideElement cvvField = $(byText("CVC/CVV")).parent().$("[class=\"input__control\"]");

    private final SelenideElement approvedOperation = $(byText("Операция одобрена Банком.")).parent().$("[class=\"notification__content\"]");
    private final SelenideElement failureOperation = $(byText("Ошибка! Банк отказал в проведении операции.")).parent().$("[class=\"notification__content\"]");
    private final SelenideElement wrongFormatError = $(byText("Неверный формат"));
    private final ElementsCollection wrongFormat4Error = $$(byText("Неверный формат"));
    private final SelenideElement cardExpirationDateError = $(byText("Неверно указан срок действия карты"));
    private final SelenideElement cardExpiredError = $(byText("Истёк срок действия карты"));
    private final SelenideElement requiredFieldError = $(byText("Поле обязательно для заполнения"));

    private final SelenideElement cancelField = $$("[class=\"icon-button__text\"]").first();
    private final SelenideElement continueButton = $$("button").find(exactText("Продолжить"));

    public BuyForm() {
        SelenideElement heading = $$("h3").find(exactText("Оплата по карте"));
        heading.shouldBe(visible);
    }

    public void inputData(DataHelper.Card card) {
        cardNumberField.setValue(card.getCardNumber());
        monthField.setValue(card.getMonth());
        yearField.setValue(card.getYear());
        cardHolderField.setValue(card.getCardHolder());
        cvvField.setValue(card.getCvv());
        continueButton.click();
    }

    public void waitNotificationApproved() {
        approvedOperation.shouldHave(Condition.text("Операция одобрена Банком."), Duration.ofSeconds(15))
                .shouldBe(Condition.visible);
        cancelField.click();
    }

    public void waitNotificationFailure() {
        failureOperation.shouldHave(Condition.text("Ошибка! Банк отказал в проведении операции."), Duration.ofSeconds(15))
                .shouldBe(Condition.visible);
    }

    public void waitNotificationWrongFormat() {
        wrongFormatError.shouldHave(Condition.text("Неверный формат"))
                .shouldBe(Condition.visible);
    }

    public void waitNotificationExpirationDateError() {
        cardExpirationDateError.shouldHave(Condition.text("Неверно указан срок действия карты"))
                .shouldBe(Condition.visible);
    }

    public void waitNotificationExpiredError() {
        cardExpiredError.shouldHave(Condition.text("Истёк срок действия карты"))
                .shouldBe(Condition.visible);
    }

    public void waitNotificationWrongFormat4Fields() {
        assertEquals(4, wrongFormat4Error.size());
        requiredFieldError.shouldHave(Condition.text("Поле обязательно для заполнения"))
                .shouldBe(Condition.visible);
    }
}