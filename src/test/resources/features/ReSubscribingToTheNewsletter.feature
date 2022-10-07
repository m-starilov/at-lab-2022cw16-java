#JiraTicket: id = 16365,  url = "https://jira.epam.com/jira/browse/EPMFARMATS-16365
@ui
@bdd
Feature: Checking the possibility to subscribe to the newsletter twice

  Scenario: Checking the possibility to subscribe to the newsletter twice
    Given  Random email address generated using Jfairy java library.
    When I open Home Page
    Then I see "Home page" page title
    When User find field Newsletter, send email and press confirm key
    Then Alert success message appears: "Newsletter : You have successfully subscribed to this newsletter."
    When User find field Newsletter, send email and press confirm key
    Then Alert error message appears: "Newsletter : This email address is already registered."
