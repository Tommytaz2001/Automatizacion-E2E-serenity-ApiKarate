package com.nttdata.qa.ui;

import net.serenitybdd.screenplay.targets.Target;

public class ConfirmationPageTargets {
    public static final Target CONFIRMATION_HEADER =
        Target.the("order confirmation header").locatedBy(".complete-header");
}
