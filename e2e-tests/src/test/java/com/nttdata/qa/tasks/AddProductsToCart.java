package com.nttdata.qa.tasks;

import com.nttdata.qa.ui.ProductsPageTargets;
import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.Task;
import net.serenitybdd.screenplay.actions.Click;
import net.serenitybdd.screenplay.annotations.Subject;

@Subject("add #count products to the cart")
public class AddProductsToCart implements Task {

    private final int count;

    private AddProductsToCart(int count) {
        this.count = count;
    }

    @Override
    public <T extends Actor> void performAs(T actor) {
        for (int i = 1; i <= count; i++) {
            actor.attemptsTo(Click.on(ProductsPageTargets.addToCartButtonAt(i)));
        }
    }

    public static AddProductsToCart count(int numberOfProducts) {
        return new AddProductsToCart(numberOfProducts);
    }
}
