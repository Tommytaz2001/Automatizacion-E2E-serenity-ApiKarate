package com.nttdata.qa.tasks;

import com.nttdata.qa.ui.CartPageTargets;
import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.Task;
import net.serenitybdd.screenplay.actions.Click;
import net.serenitybdd.screenplay.annotations.Subject;

@Subject("navigate to the shopping cart")
public class NavigateToCart implements Task {

    @Override
    public <T extends Actor> void performAs(T actor) {
        actor.attemptsTo(Click.on(CartPageTargets.CART_ICON));
    }

    public static NavigateToCart page() {
        return new NavigateToCart();
    }
}
