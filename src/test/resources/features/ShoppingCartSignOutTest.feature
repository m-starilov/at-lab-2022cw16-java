#JiraTicket: id = 16335,  url = https://jira.epam.com/jira/browse/EPMFARMATS-16335
@ui
@bdd
Feature: Shopping cart test

  Scenario: Check shopping cart

    Given I open Home Page
    Then Home page is opened

    When I click to Sign in button
    Then Authentication page is opened
    And I see a create account form
    And I see an already registered form

    When I click Proceed to My Account button
    Then I see An email address required message
    
    When I log in with email "rescuerangers@mail.com" and password "123123"
    Then I see Authentication failed message

    When I log in with email "rescue-rangers@mail.com" and password "123123"
    Then I see Authentication failed message

    When I log in with email "rescue-rangers@mail.com" and password "010203"
    Then My account page is opened

    When I go to Catalog page
    And I put one item "Faded Short Sleeve T-shirts" in a cart
    And I press Proceed to checkout button to Order Summary Page
    Then added product "Faded Short Sleeve T-shirts" displayed on the page

    When I click Sign out button
    Then Cart page has Empty cart alert message
    And Mini cart is empty
    And Sign in button is displayed

    When I click to Sign in button
    Then Authentication page is opened
    And I see a create account form
    And I see an already registered form

    When I log in with email "rescue-rangers@mail.com" and password "010203"
    Then My account page is opened

    When I click mini cart button
    Then Cart page has Empty cart alert message
    And Mini cart is empty