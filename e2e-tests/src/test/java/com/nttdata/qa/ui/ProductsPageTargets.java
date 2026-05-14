package com.nttdata.qa.ui;

import net.serenitybdd.screenplay.targets.Target;

public class ProductsPageTargets {
    public static Target addToCartButtonAt(int index) {
        return Target.the("add to cart button #" + index)
            .locatedBy("(//button[starts-with(@data-test,'add-to-cart')])[" + index + "]");
    }
}
