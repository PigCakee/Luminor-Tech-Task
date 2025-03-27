package com.app.demo.feature.auth.ui.validator

import com.app.demo.utility.validator.EmailFieldValidator
import com.google.common.truth.Truth.assertThat
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.Arguments
import org.junit.jupiter.params.provider.MethodSource
import java.util.stream.Stream

class EmailFieldValidatorTest {

    @ParameterizedTest
    @MethodSource("provideEmails")
    fun `check email validation`(email: String, isValid: Boolean) {
        val allowedLengthEmail = EmailFieldValidator.takeAllowedLength(email)

        val emailIsValid = EmailFieldValidator.validate(allowedLengthEmail)
        assertThat(emailIsValid).isEqualTo(isValid)
    }

    companion object {
        @JvmStatic
        fun provideEmails(): Stream<Arguments> {
            var longEmail = ""
            repeat(EmailFieldValidator.MAX_LENGTH * 2) { longEmail += "a" }
            return Stream.of(
                Arguments.of(longEmail, false),
                Arguments.of("testtest.com", false),
                Arguments.of("test@testcom", false),
                Arguments.of("test@test.", false),
                Arguments.of("test@.com", false),
                Arguments.of("test@test.com", true),
            )
        }
    }
}
