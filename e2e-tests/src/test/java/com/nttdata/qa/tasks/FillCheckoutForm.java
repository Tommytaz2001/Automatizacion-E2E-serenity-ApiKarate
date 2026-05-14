package com.nttdata.qa.tasks;

import com.nttdata.qa.ui.CartPageTargets;
import com.nttdata.qa.ui.CheckoutPageTargets;
import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.Task;
import net.serenitybdd.screenplay.actions.Click;
import net.serenitybdd.screenplay.actions.Enter;
import net.serenitybdd.screenplay.annotations.Subject;

@Subject("fill the checkout form")
public class FillCheckoutForm implements Task {

    private final String firstName;
    private final String lastName;
    private final String zipCode;

    private FillCheckoutForm(String firstName, String lastName, String zipCode) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.zipCode = zipCode;
    }

    @Override
    public <T extends Actor> void performAs(T actor) {
        actor.attemptsTo(
            Click.on(CartPageTargets.CHECKOUT_BUTTON),
            Enter.theValue(firstName).into(CheckoutPageTargets.FIRST_NAME_FIELD),
            Enter.theValue(lastName).into(CheckoutPageTargets.LAST_NAME_FIELD),
            Enter.theValue(zipCode).into(CheckoutPageTargets.POSTAL_CODE_FIELD)
        );
    }

    public static FillCheckoutForm with(String firstName, String lastName, String zipCode) {
        return new FillCheckoutForm(firstName, lastName, zipCode);
    }
}
