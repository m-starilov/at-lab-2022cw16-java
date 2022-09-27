Feature: As user I want to add items to wishlist and edit it

  Scenario: Edit wishlist
    When I open main page
    Then the website opens and Home page is displayed
    When I sign in with valid credentials
    Then My account page opened
    When I click "Home" button
    Then Home page opened
    When I chose "Summer dresses" from site top menu (Women>Dresses>Summer Dresses)
    Then page with Summer dresses opened
    When I change items view to List
    Then items displayed in List view with "Add to cart", "More" buttons and "Add to Wishlist", "Add to compare" links
    When I add item 1 to Wishlist
    Then infobox is displayed
    When I close infobox
    And in "Add to Wishlist" link outline heart icon for item 1 changed to solid
    When I add item 2 to Wishlist
    Then infobox is displayed
    When I close infobox
    And in "Add to Wishlist" link outline heart icon for item 2 changed to solid
    When I add item 3 to Wishlist
    Then infobox is displayed
    When I close infobox
    And in "Add to Wishlist" link outline heart icon for item 3 changed to solid
    When I go to Wishlist page (My account>My Wishlists)
    Then My Wishlists page displayed with one "My wishlist" included 3 items
    When I click "View" link
    Then displayed 3 added items
    When I delete one item
    Then displayed 2 added items
    When I chose "T-shirts" from site top menu (Women>Dresses>T-shirts)
    Then page with T-shirts opened
    When I add item 1 to Wishlist
    Then infobox is displayed
    When I close infobox
    And in "Add to Wishlist" link outline heart icon changed to solid
    When I chose "Evening Dresses" from site top menu (Women>Dresses>Evening Dresses)
    Then page with Evening Dresses opened
    When I add item 1 to Wishlist
    Then infobox is displayed
    When I close infobox
    And in "Add to Wishlist" link outline heart icon changed to solid
    When I go to Wishlist page (My account>My Wishlists)
    Then My Wishlists page displayed with one "My wishlist" included 4 items
    When I click "My wishlist" link
    Then Displayed 4 added items
    When I click cross icon in table
    Then confirmation message with request (OK and Cancel) displayed
    When I click "OK" button
    Then no Wishlist displayed on page