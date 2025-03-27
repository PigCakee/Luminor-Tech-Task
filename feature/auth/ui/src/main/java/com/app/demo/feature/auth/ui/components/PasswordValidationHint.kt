package com.app.demo.feature.auth.ui.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.app.demo.feature.auth.ui.R
import com.app.demo.ui.theme.DemoTheme
import com.app.demo.utility.validator.PasswordValidationRule
import kotlinx.collections.immutable.PersistentSet
import kotlinx.collections.immutable.persistentSetOf

@Composable
fun PasswordValidationHint(
    satisfiedRules: PersistentSet<PasswordValidationRule>,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .then(modifier)
    ) {
        PasswordValidationHintItem(
            hint = stringResource(id = R.string.password_validation_rule_minimum_length),
            isSatisfied = getIsSatisfied(satisfiedRules, PasswordValidationRule.MINIMUM_LENGTH)
        )
        PasswordValidationHintItem(
            hint = stringResource(id = R.string.password_validation_rule_special_character),
            isSatisfied = getIsSatisfied(satisfiedRules, PasswordValidationRule.SPECIAL_CHARACTER)
        )
        PasswordValidationHintItem(
            hint = stringResource(id = R.string.password_validation_rule_lowercase_letter),
            isSatisfied = getIsSatisfied(satisfiedRules, PasswordValidationRule.LOWERCASE_LETTER)
        )
        PasswordValidationHintItem(
            hint = stringResource(id = R.string.password_validation_rule_uppercase_letter),
            isSatisfied = getIsSatisfied(satisfiedRules, PasswordValidationRule.UPPERCASE_LETTER)
        )
        PasswordValidationHintItem(
            hint = stringResource(id = R.string.password_validation_rule_digit),
            isSatisfied = getIsSatisfied(satisfiedRules, PasswordValidationRule.DIGIT)
        )
    }
}

private fun getIsSatisfied(
    satisfiedRules: PersistentSet<PasswordValidationRule>,
    rule: PasswordValidationRule,
): Boolean? = when {
    satisfiedRules.isEmpty() -> null
    else -> satisfiedRules.contains(rule)
}

@Preview(showBackground = true, name = "Neutral state - no rules satisfied")
@Composable
fun PasswordValidationHintNeutralPreview() {
    DemoTheme {
        // When no rule is satisfied, all items are neutral (null)
        Column(modifier = Modifier.padding(16.dp)) {
            Text(text = "Neutral State (No rules satisfied)")
            Spacer(modifier = Modifier.height(8.dp))
            PasswordValidationHint(satisfiedRules = persistentSetOf())
        }
    }
}

@Preview(showBackground = true, name = "Mixed state - some rules satisfied")
@Composable
fun PasswordValidationHintMixedPreview() {
    DemoTheme {
        // When some rules are satisfied, the ones included will be true and the others false.
        // For instance, MINIMUM_LENGTH and DIGIT are satisfied.
        Column(modifier = Modifier.padding(16.dp)) {
            Text(text = "Mixed State (Some rules satisfied)")
            Spacer(modifier = Modifier.height(8.dp))
            PasswordValidationHint(
                satisfiedRules = persistentSetOf(
                    PasswordValidationRule.MINIMUM_LENGTH,
                    PasswordValidationRule.DIGIT
                )
            )
        }
    }
}