package com.ness.automation.assertions;

import org.testng.Assert;

import io.qameta.allure.Allure;
import io.qameta.allure.Step;

public class AssertBudgetInCart {

    @Step("Assert budget in cart")
    public static void assertCartTotalNotExceeds(int budget, int count, Integer cartTotalPrice) {

        if (cartTotalPrice == null) {
            Allure.step("No items added to cart");
            return;
        }
        int totalBudgetAllowed = budget * count;
        Assert.assertTrue(cartTotalPrice <= totalBudgetAllowed,
                String.format("cart total price : %s exceed the allowed total budget:%s ", cartTotalPrice,
                        totalBudgetAllowed));
    }
}