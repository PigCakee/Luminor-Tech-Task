package com.app.demo.feature.auth.ui.validator

import com.app.demo.utility.validator.SignUpPasswordFieldValidator
import com.google.common.truth.Truth.assertThat
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.Arguments
import org.junit.jupiter.params.provider.MethodSource
import java.util.stream.Stream

class SignUpPasswordFieldValidatorTest {

    @ParameterizedTest
    @MethodSource("providePasswords")
    fun `validate password`(password: String, isValid: Boolean) {
        val result = SignUpPasswordFieldValidator.getSatisfiedValidationRules(password)
        assertThat(result.size == 5).isEqualTo(isValid)
    }

    companion object {
        @JvmStatic
        fun providePasswords(): Stream<Arguments> {
            return Stream.of(
                Arguments.of("Password1!", true),
                Arguments.of("password", false),
                Arguments.of("PASSWORD1", false),
                Arguments.of("Password", false),
                Arguments.of("Passw1!", false),
                Arguments.of("12345678", false),
                Arguments.of("Password1", false),
                Arguments.of("P@ss1", false)
            )
        }
    }
}
