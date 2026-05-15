package com.nttdata.qa.runners;

import io.cucumber.junit.CucumberOptions;
import net.serenitybdd.cucumber.CucumberWithSerenity;
import org.junit.runner.RunWith;

/**
 * Runner dedicado para pruebas de humo (smoke tests).
 * Ejecuta solo los escenarios marcados con @Smoke.
 * Util para validaciones rápidas post-despliegue.
 *
 * Uso: mvnw.cmd test -pl e2e-tests -Dcucumber.filter.tags="@Smoke"
 */
@RunWith(CucumberWithSerenity.class)
@CucumberOptions(
    plugin = {
        "pretty",
        "json:target/cucumber-reports/smoke-cucumber.json",
        "html:target/cucumber-reports/smoke-html-report"
    },
    features = "src/test/resources/features",
    glue = "com.nttdata.qa.stepdefinitions",
    tags = "@Smoke"
)
public class SmokeTestRunner {
}
