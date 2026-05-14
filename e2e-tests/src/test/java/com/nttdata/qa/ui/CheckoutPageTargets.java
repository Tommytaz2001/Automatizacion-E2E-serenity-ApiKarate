package com.nttdata.qa.ui;

import net.serenitybdd.screenplay.targets.Target;

public class CheckoutPageTargets {
    public static final Target FIRST_NAME_FIELD =
        Target.the("first name input").locatedBy("#first-name");
    public static final Target LAST_NAME_FIELD =
        Target.the("last name input").locatedBy("#last-name");
    public static final Target POSTAL_CODE_FIELD =
        Target.the("postal code input").locatedBy("#postal-code");
    public static final Target CONTINUE_BUTTON =
        Target.the("continue button").locatedBy("[data-test='continue']");
    public static final Target FINISH_BUTTON =
        Target.the("finish button").locatedBy("[data-test='finish']");
}
