#JiraTicket: id = 16346,  url = https://jira.epam.com/jira/browse/EPMFARMATS-16346

Feature: Mini cart

  Scenario: Check mini cart

    When I open Summer Dresses page
    Then Catalog page was opened and list of 3 items

    When I add 2 items to cart
    Then I see mini cart quantity is "2"

    When I move cursor to mini cart
    Then I see mini cart is displayed as a block with 2 items

    When I open item page from mini cart list
    Then Item page is opened. I see correct title

    When I move cursor to mini cart
    Then I see mini cart is displayed as a block with 2 items

    When I delete 1 item from mini cart
    Then I see mini cart quantity "1" and 1 item in list

    When I go to cart page from mini cart by click Check out button
    Then Cart page is opened. I see shopping cart with "1" item and "1 Product" summary products quantity

    When I move cursor to mini cart
    Then I see mini cart is displayed as a block with 1 items

    When I delete 1 item from mini cart
    Then I see empty mini cart and cart page has "Your shopping cart is empty." alert message

