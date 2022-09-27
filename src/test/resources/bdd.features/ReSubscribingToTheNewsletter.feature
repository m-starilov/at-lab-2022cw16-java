Feature: Checking the possibility to subscribe to the newsletter twice

  Scenario: Checking the possibility to subscribe to the newsletter twice
    Given  Random email address generated using Jfairy java library.
    When User open automationpractice.com website
    Then Website opened
    When User find field Newsletter, send email and press confirm key
    Then Alert success message appears: "Newsletter : You have successfully subscribed to this newsletter."
    When User find field Newsletter, send email and press confirm key
    Then Alert error message appears: "Newsletter : This email address is already registered."
