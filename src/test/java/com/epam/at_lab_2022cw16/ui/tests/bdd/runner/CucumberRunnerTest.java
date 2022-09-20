package com.epam.at_lab_2022cw16.ui.tests.bdd.runner;


import io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;
import org.junit.runner.RunWith;

@RunWith(Cucumber.class)
@CucumberOptions(
        plugin = {"pretty", "html:target/cucumber-reports/CucumberTest.html",
                "junit:target/cucumber-reports/CucumberTest.xml"},
        monochrome = true,
        glue = "com.epam.at_lab_2022cw16.ui",
        features = "src/test/resources/bdd/features"
)

public class CucumberRunnerTest {
}
