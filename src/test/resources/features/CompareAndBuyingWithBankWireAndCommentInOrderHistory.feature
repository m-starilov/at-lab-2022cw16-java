#JiraTicket: id = {16336, 16364},  url = {"https://jira.epam.com/jira/browse/EPMFARMATS-16336#",
# "https://jira.epam.com/jira/browse/EPMFARMATS-16364"})

Feature: Compare And Buying With BankWire, Comment In Order History

  Scenario: Compare And Buying With BankWire And Comment In Order History

    When I open Home Page
    And I click to Sign in button
    And I log in with email "mofrekoiquemma-6157@yopmail.com" and password "12345"
    Then I see "My Account" page title

    When I go to Catalog page
    And I add to Compare product with id 5
    And I add to Compare product with id 6
    And I click to Compare button
    Then I see 2 items is displayed

    When I add to Cart product with id 5
    And I click Continue shopping button
    And I delete from comparison item with id 5
    Then I see 1 items is displayed

    When I delete from comparison item with id 6
    Then I see 0 items is displayed

    When I open Cart page
    Then I see "1 Product" in cart

    When I enter "qwe" to Qty field
    Then I see "1 Product" in cart

    When I enter "-2" to Qty field
    Then I see "1 Product" in cart

    When I enter "2" to Qty field
    Then I see "2 Products" in cart

    When I click Proceed to checkout button at Summary page
    Then I see "Addresses" page title

    When I click Proceed to checkout button at Address page
    Then I see "Shipping" page title

    When I click Proceed to checkout button at Shipping page
    Then I see modal window is displayed

    When I close modal window
    And I change checkbox state
    And I click Proceed to checkout button at Shipping page
    Then I see "Order Payment" page title

    When I choose Pay by bank wire method
    Then I see "Bank wire payment" page title

    When I click I confirm my order button
    Then I see "Confirmation page" page title
    And I see "Your order on My Store is complete." text is displayed
    And I see amount "$59.96"
    And I see correct bank information

    When I proceed to My Account page
    Then I see "My Account" page title

    When I go to Order History page from My Account Page
    And I open details of last order
    And I click Send button
    Then I see Alert-Danger message at Order History page

    When I select product with id "5" from dropdown menu
    And I send text message "Please, send me photo of it!"
    And I click Send button
    Then I see Alert-Success message at Order History page
    And I see user message "Please, send me photo of it!"