package com.nttdata.qa.stepdefinitions;

import com.nttdata.qa.questions.ConfirmationMessage;
import com.nttdata.qa.tasks.AddProductsToCart;
import com.nttdata.qa.tasks.ConfirmPurchase;
import com.nttdata.qa.tasks.FillCheckoutForm;
import com.nttdata.qa.tasks.Login;
import com.nttdata.qa.tasks.NavigateToCart;
import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.abilities.BrowseTheWeb;
import net.serenitybdd.screenplay.actions.Open;
import net.serenitybdd.screenplay.actors.OnStage;
import net.serenitybdd.screenplay.actors.OnlineCast;
import net.thucydides.core.webdriver.ThucydidesWebDriverSupport;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

import static net.serenitybdd.screenplay.GivenWhenThen.seeThat;
import static org.hamcrest.Matchers.containsString;

public class PurchaseStepDefinitions {

    private WebDriver driver;
    private Actor shopper;

    @Before
    public void setTheStage() throws Exception {
        // Chrome preferences: disable password manager popup (appears when Chrome detects
        // that secret_sauce is a known-breached password)
        Map<String, Object> prefs = new HashMap<>();
        prefs.put("credentials_enable_service", false);
        prefs.put("profile.password_manager_enabled", false);
        prefs.put("profile.password_manager_leak_detection", false);

        ChromeOptions options = new ChromeOptions();
        options.addArguments(
            "--no-sandbox",
            "--disable-dev-shm-usage",
            "--disable-gpu",
            "--window-size=1920,1080",
            "--disable-features=PasswordLeakDetection",
            "--no-first-run"
        );
        options.setExperimentalOption("prefs", prefs);

        File driverFile = new File("../drivers/chromedriver-win64/chromedriver.exe").getCanonicalFile();
        System.setProperty("webdriver.chrome.driver", driverFile.getAbsolutePath());

        driver = new ChromeDriver(options);

        // Register driver with Serenity so step outcomes and screenshots are recorded
        ThucydidesWebDriverSupport.useDriver(driver);

        OnStage.setTheStage(new OnlineCast());
        shopper = OnStage.theActorCalled("Shopper");
        shopper.can(BrowseTheWeb.with(driver));
    }

    @After
    public void cleanUp() {
        if (driver != null) {
            driver.quit();
            driver = null;
        }
    }

    @Given("the user navigates to the SauceDemo application")
    public void navigateToApp() {
        shopper.attemptsTo(Open.url("https://www.saucedemo.com"));
    }

    @When("the user logs in with username {string} and password {string}")
    public void logIn(String username, String password) {
        shopper.attemptsTo(Login.withCredentials(username, password));
    }

    @And("the user adds {int} products to the cart")
    public void addProducts(int count) {
        shopper.attemptsTo(AddProductsToCart.count(count));
    }

    @And("the user navigates to the shopping cart")
    public void goToCart() {
        shopper.attemptsTo(NavigateToCart.page());
    }

    @And("the user proceeds to checkout")
    public void proceedToCheckout() {
        // Checkout button click is inside FillCheckoutForm since it navigates to the form page
    }

    @And("the user fills the checkout form with firstName {string}, lastName {string}, and zipCode {string}")
    public void fillCheckout(String firstName, String lastName, String zipCode) {
        shopper.attemptsTo(FillCheckoutForm.with(firstName, lastName, zipCode));
    }

    @And("the user confirms the purchase")
    public void confirmPurchase() {
        shopper.attemptsTo(ConfirmPurchase.order());
    }

    @Then("the user should see the confirmation message {string}")
    public void verifyConfirmation(String expectedMessage) {
        shopper.should(seeThat(ConfirmationMessage.displayed(), containsString(expectedMessage)));
    }
}
