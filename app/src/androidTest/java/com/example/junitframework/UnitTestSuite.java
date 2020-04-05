package com.example.junitframework;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;

// Runs all unit tests.
@RunWith(Suite.class)
@Suite.SuiteClasses({CalculatorInstrumentationTest.class,
        CalculatorAddParameterizedTest.class})
public class UnitTestSuite {
}
