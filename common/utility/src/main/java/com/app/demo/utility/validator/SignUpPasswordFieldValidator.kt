package com.app.demo.utility.validator

object SignUpPasswordFieldValidator {

    const val VALID_PASSWORD_RULE_SIZE = 5

    fun getSatisfiedValidationRules(password: String?): Set<PasswordValidationRule> {
        val satisfiedRules = mutableSetOf<PasswordValidationRule>()

        // Return empty set in case password is null (initial password state)
        if (password == null) return emptySet()

        // Check each validation rule and add to the set if the rule is satisfied
        if (password.length >= 8) {
            satisfiedRules.add(PasswordValidationRule.MINIMUM_LENGTH)
        }
        if (password.any { it in "!@#$%^&*()-_=+{}[]|:;\"'<>,.?/" }) {
            satisfiedRules.add(PasswordValidationRule.SPECIAL_CHARACTER)
        }
        if (password.any { it.isLowerCase() }) {
            satisfiedRules.add(PasswordValidationRule.LOWERCASE_LETTER)
        }
        if (password.any { it.isUpperCase() }) {
            satisfiedRules.add(PasswordValidationRule.UPPERCASE_LETTER)
        }
        if (password.any { it.isDigit() }) {
            satisfiedRules.add(PasswordValidationRule.DIGIT)
        }

        return satisfiedRules
    }
}