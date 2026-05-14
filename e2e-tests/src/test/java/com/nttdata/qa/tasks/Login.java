package com.nttdata.qa.tasks;

import com.nttdata.qa.ui.LoginPageTargets;
import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.Task;
import net.serenitybdd.screenplay.actions.Click;
import net.serenitybdd.screenplay.actions.Enter;
import net.serenitybdd.screenplay.annotations.Subject;

@Subject("log in with credentials")
public class Login implements Task {

    private final String username;
    private final String password;

    private Login(String username, String password) {
        this.username = username;
        this.password = password;
    }

    @Override
    public <T extends Actor> void performAs(T actor) {
        actor.attemptsTo(
            Enter.theValue(username).into(LoginPageTargets.USERNAME_FIELD),
            Enter.theValue(password).into(LoginPageTargets.PASSWORD_FIELD),
            Click.on(LoginPageTargets.LOGIN_BUTTON)
        );
    }

    public static Login withCredentials(String username, String password) {
        return new Login(username, password);
    }
}
