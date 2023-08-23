package ru.netology.pages;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.SelenideElement;

import static com.codeborne.selenide.Condition.exactText;
import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.$$;

public class PaymentMethod {
    private final SelenideElement buyButton = $$("button").find(exactText("Купить"));
    private final SelenideElement creditButton = $$("button").find(exactText("Купить в кредит"));

    public PaymentMethod() {
        SelenideElement heading = $$("h2").find(Condition.text("Путешествие дня"));
        heading.shouldBe(visible);
    }

    public BuyForm goToBuyPage() {
        buyButton.click();
        return new BuyForm();
    }

    public CreditForm goToCreditPage() {
        creditButton.click();
        return new CreditForm();
    }
}