Feature: Compare And Buying With BankWire

  Scenario: Compare And Buying With BankWire
    When I open Home Page
    And I click to Sign in button
    And I enter correct credentials to Authentication form
    Then I see "My account" page title
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
    Then I see "Addresses" navigation page title
    When I click Proceed to checkout button at Address page
    Then I see "Shipping" navigation page title
    When I click Proceed to checkout button at Shipping page
    Then I see modal window is displayed
    When I close modal window
    And I change checkbox state
    And I click Proceed to checkout button at Shipping page
    Then I see "Your payment method" navigation page title
    When I choose Pay by bank wire method
    Then I see "Bank-wire payment." navigation page title
    When I click I confirm my order button
    Then I see "Order confirmation" navigation page title
    And I see "Your order on My Store is complete." text is displayed
    And I see amount "$59.96"
    And I see correct bank information