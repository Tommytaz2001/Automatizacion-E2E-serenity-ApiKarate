package com.nttdata.qa.tasks;

import com.nttdata.qa.ui.CheckoutPageTargets;
import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.Task;
import net.serenitybdd.screenplay.actions.Click;
import net.serenitybdd.screenplay.annotations.Subject;

@Subject("confirm the purchase")
public class ConfirmPurchase implements Task {

    @Override
    public <T extends Actor> void performAs(T actor) {
        actor.attemptsTo(
            Click.on(CheckoutPageTargets.CONTINUE_BUTTON),
            Click.on(CheckoutPageTargets.FINISH_BUTTON)
        );
    }

    public static ConfirmPurchase order() {
        return new ConfirmPurchase();
    }
}
