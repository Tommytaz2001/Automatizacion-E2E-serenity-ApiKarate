@E2E @SauceDemo
Feature: Complete E-commerce Purchase Flow
  As a registered user of SauceDemo
  I want to add products to the cart and complete a purchase
  So that I can verify the end-to-end checkout flow works correctly

  Background:
    Given the user navigates to the SauceDemo application

  @Smoke
  Scenario Outline: User completes a full purchase with two products
    When the user logs in with username "<username>" and password "<password>"
    And the user adds <productCount> products to the cart
    And the user navigates to the shopping cart
    And the user proceeds to checkout
    And the user fills the checkout form with firstName "<firstName>", lastName "<lastName>", and zipCode "<zipCode>"
    And the user confirms the purchase
    Then the user should see the confirmation message "THANK YOU FOR YOUR ORDER"

    # Test data sourced from src/test/resources/testdata/users.csv
    Examples:
      | username      | password     | productCount | firstName | lastName | zipCode |
      | standard_user | secret_sauce | 2            | John      | Doe      | 12345   |
      | standard_user | secret_sauce | 2            | Jane      | Smith    | 67890   |
