package com.nttdata.qa.ui;

import net.serenitybdd.screenplay.targets.Target;

public class CartPageTargets {
    public static final Target CART_ICON =
        Target.the("shopping cart icon").locatedBy(".shopping_cart_link");
    public static final Target CHECKOUT_BUTTON =
        Target.the("checkout button").locatedBy("[data-test='checkout']");
}
