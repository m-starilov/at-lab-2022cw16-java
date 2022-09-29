package com.epam.at_lab_2022cw16.ui.tests.bdd.runner;

import org.junit.platform.suite.api.IncludeEngines;
import org.junit.platform.suite.api.SelectClasspathResource;
import org.junit.platform.suite.api.Suite;

@Suite
@IncludeEngines("cucumber")
@SelectClasspathResource("bdd.features")
public class RunCucumberTest {
}
