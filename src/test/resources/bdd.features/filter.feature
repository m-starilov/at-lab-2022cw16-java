  #JiraTicket: id = 16307,  url = "https://jira.epam.com/jira/browse/EPMFARMATS-16307

  Feature: As a user I want to get ability to sort and filter products in the catalog

    Scenario: Should be able sort and filter products
      Given the user opens CatalogPage
      Then the CatalogPage opened
      When the user click on dropdown list Sort by and select sort "price:desc"
      Then all products are displayed on the page according sort "price:desc"
      When the user click on dropdown list Sort by and select sort "name:desc"
      Then all products are displayed on the page according sort "name:desc"
      When the user click on dropdown list Sort by and select sort "price:asc"
      Then all products are displayed on the page according sort "name:asc"
      When the user click on dropdown list Sort by and select sort "name:asc"
      Then all products are displayed on the page according sort "name:asc"
      When the catalog sorted by "name:asc"
      When the user apply "YELLOW" filter
      Then applied "YELLOW" filter marked in the filter block by "RED" red color.
      And all products filtered by "YELLOW" color
      And the breadcrumbs on the filter page contains the name of the applied "YELLOW" filter.
      When the user apply another one price filter
      Then the page displays products that match the price filter
      And filters type "price: ", "color: yellow" and filters name are displayed in the Enabled filters: on the left side of the page.
      When the user remove all filters
      Then the page displays products sorted by the condition from first step "name:asc".
