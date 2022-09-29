#JiraTicketsLink(id = 16356,1637 url = https://jira.epam.com/jira/browse/EPMFARMATS-16356, https://jira.epam.com/jira/browse/EPMFARMATS-16357)
Feature: As a user I want to get ability to store order info in order history

  Scenario: Should store order info in order history

    When I open WomenCatalogPage
    Then I see "Women" page title

    When I put one item "Faded Short Sleeve T-shirts" in a cart
    Then alert is displayed with a message "Product successfully added to your shopping cart"
    And item  "Faded Short Sleeve T-shirts" displayed in a cart

    When I press Proceed to checkout button to Order Summary Page
    Then I see "Shopping Cart" page title
    And added product "Faded Short Sleeve T-shirts" displayed on the page

    When I click Proceed to checkout button at Summary page
    Then Authentication page is opened

    When I log in with valid email "e1@gh.com" address and password "22222222"
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
    And A message "Your order on My Store is complete."  displayed
    And order reference is on the page

    When I press Back to orders
    Then Order history page opened. The list of orders contains an order with the current order number.

    When I open Home Page
    And I click to My account button
    Then I see "My Account" page title

    When I go to Order History page from My Account Page
    Then I see "Order History" page title

    When I click Details button
    Then order information is displayed at the bottom of the page (expected product name "Faded Short Sleeve T-shirts" and quantity 1)

    When I press Reorder button in the block with information about the order
    Then I see "Shopping Cart" page title
    And  The 1 reordered item is in the cart

    When I return to Order history page
    Then I see "Order History" page title

    When I press Reorder button in block with list of orders
    Then I see "Shopping Cart" page title
    And The 1 reordered item is in the cart
    And Previews items is deleted

    When I click Proceed to checkout button at Summary page
    Then I see "Addresses" page title

    When I click Proceed to checkout button at Address page
    Then I see "Shipping" page title

    When I change checkbox state
    And I click Proceed to checkout button at Shipping page
    Then I see "Order Payment" page title

    When I choose Pay by bank wire method
    Then I see "Bank wire payment" page title

    When I click I confirm my order button
    Then I see "Confirmation page" page title
    And A message "Your order on My Store is complete."  displayed







