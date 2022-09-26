Feature: As a user I want to get ability to store order info in order history

#JiraTicket id = 16356, url = "https://jira.epam.com/jira/browse/EPMFARMATS-16356"

  Scenario: Should store order info in order history

    When I open catalog page
    Then catalog page is opened. Products are displayed on the page

    When I put one item "Faded Short Sleeve T-shirts" in a cart
    Then alert is displayed with a message "Product successfully added to your shopping cart"
    And item  "Faded Short Sleeve T-shirts" displayed in a cart

    When I press Proceed to checkout button
    Then Shopping cart page is opened. Title of page is "SHOPPING-CART SUMMARY"
    And added product "Faded Short Sleeve T-shirts" displayed on the page

    When I press Proceed to checkout button on Order Summary Page
    Then Authentication page is opened. Title of page is  "AUTHENTICATION"

    When I log in with valid email "e1@gh.com" address and password "22222222"
    Then Address information page opened. Title of page is "ADDRESSES"

    When I press Proceed to checkout button on the Address page
    Then Shipping page opened. Title of page is "SHIPPING"

    When I press Proceed to checkout button on the Shipping page
    Then Alert is displayed with a message You must agree to the terms of service before continuing.

    When I click checkbox Terms of service and press Proceed to checkout button
    Then Your payment method page opened Title of page is "PLEASE CHOOSE YOUR PAYMENT METHOD"

    When I click button Pay by bank wire
    Then Payment page opened. Title of page is "ORDER SUMMARY"

    When I press I confirm my order button
    Then Order confirmation page opened. Title of page is "ORDER CONFIRMATION".
    And A message "Your order on My Store is complete."  displayed
    And order reference is on the page

    When I press Back to orders
    Then Order history page opened. The list of orders contains an order with the current order number.






