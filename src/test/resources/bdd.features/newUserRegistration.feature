#JiraTicket: id = 16320,  url = "https://jira.epam.com/jira/browse/EPMFARMATS-16320

Feature: As a user I want to get ability to create a new account

  Scenario: Registration as new user on the website

    Given the user opens new user registration page

    When  the user enters "kfhvifdjhkf" in the “Email address” field and press the “Create an account” button
    Then the email has not been verified. “Invalid email address.” is displayed.

    When the user enters "lellessakale-8336@yopmail.com" in the “Email address” field and press the “Create an account” button
    Then the email has been verified. “Create an account” page opened.

    When  the user clicks register button with empty fields in registration form
    Then a message is displayed at the top of the form with a list of incorrectly filled fields. Number of fields in the list equals the number of required fields.

    When  the user fills fields with invalid data
      | firstName | lastName    | birthMonth | birthDay | birthYear | password | address  | city    | postalCode | mobilePhone |
      | 123-dfd   | Sark-isy@an | 05         | 12       | 2005      | -------- | dfdfdfds | fdfdgfd | 3423       | er4q2       |
    Then the fields changed the color of the frame to red
    And А message is displayed at the top of the form with incorrectly filled fields.

    When  the user fills fields with valid data
      | firstName | lastName    | birthMonth | birthDay | birthYear | password | address  | city    | postalCode | mobilePhone |
      | 123-dfd   | Sark-isy@an | 05         | 12       | 2005      | -------- | dfdfdfds | fdfdgfd | 3423       | er4q2       |
    Then I see "My Account" page title