package com.nttdata.qa.runners;

import io.cucumber.junit.CucumberOptions;
import net.serenitybdd.cucumber.CucumberWithSerenity;
import org.junit.runner.RunWith;

@RunWith(CucumberWithSerenity.class)
@CucumberOptions(
    plugin = {
        "pretty",
        "json:target/cucumber-reports/cucumber.json",
        "html:target/cucumber-reports/cucumber-html-report"
    },
    features = "src/test/resources/features",
    glue = "com.nttdata.qa.stepdefinitions",
    tags = "not @wip"
)
public class CucumberTestRunner {
}
