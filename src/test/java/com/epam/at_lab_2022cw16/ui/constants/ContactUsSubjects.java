package com.epam.at_lab_2022cw16.ui.constants;

public enum ContactUsSubjects {

    CHOOSE("-- Choose --"),
    CUSTOMER_SERVICE("Customer service"),
    WEBMASTER("Webmaster");

    private final String subject;

    ContactUsSubjects(String subject) {
        this.subject = subject;
    }

    public String getSubject() {
        return subject;
    }
}
