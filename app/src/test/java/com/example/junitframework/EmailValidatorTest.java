package com.example.junitframework;

import com.google.common.truth.Truth;
import org.junit.Test;

public class EmailValidatorTest {
    @Test
    public void emailValidator_CorrectEmailSimple_ReturnsTrue() {
        Truth.assertThat(EmailValidator.isValidEmail("name@email.com")).isTrue();
    }
}
