package com.epam.at_lab_2022cw16.ui.utils;

import com.devskiller.jfairy.Fairy;
import com.devskiller.jfairy.producer.person.Person;

public class RandomEmailCreator {

    public static String generateRandomEmail() {
        Fairy fairy = Fairy.create();
        Person person = fairy.person();
        return person.getEmail();
    }
}
