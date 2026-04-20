package com.ness.automation.assertions;

import org.testng.Assert;

import io.qameta.allure.Step;   

public class AssertBudgetInCart {

    @Step("Assert budget in cart")
    public static void assertBudgetInCart(int budget, int count, int cartTotalPrice) {
        int totalBudgetAllowed = budget * count;
        Assert.assertTrue(cartTotalPrice <= totalBudgetAllowed,
                String.format("cart total price : %s exceed the allowed total budget:%s ", cartTotalPrice,
                        totalBudgetAllowed));
    }
}