package com.nttdata.qa.ui;

import net.serenitybdd.screenplay.targets.Target;

public class LoginPageTargets {
    public static final Target USERNAME_FIELD =
        Target.the("username input field").locatedBy("#user-name");
    public static final Target PASSWORD_FIELD =
        Target.the("password input field").locatedBy("#password");
    public static final Target LOGIN_BUTTON =
        Target.the("login button").locatedBy("#login-button");
}
