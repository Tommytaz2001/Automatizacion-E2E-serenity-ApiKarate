package com.nttdata.qa.questions;

import com.nttdata.qa.ui.ConfirmationPageTargets;
import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.Question;
import net.serenitybdd.screenplay.annotations.Subject;
import net.serenitybdd.screenplay.questions.Text;

@Subject("the order confirmation message")
public class ConfirmationMessage implements Question<String> {

    @Override
    public String answeredBy(Actor actor) {
        return Text.of(ConfirmationPageTargets.CONFIRMATION_HEADER)
                   .answeredBy(actor)
                   .toUpperCase();
    }

    public static ConfirmationMessage displayed() {
        return new ConfirmationMessage();
    }
}
