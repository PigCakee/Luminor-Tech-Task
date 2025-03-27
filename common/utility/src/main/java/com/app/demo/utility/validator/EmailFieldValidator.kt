package com.app.demo.utility.validator

object EmailFieldValidator {

    const val MAX_LENGTH = 127
    private val regex by lazy { Regex("^(?=[^\\.])[0-9a-zA-Z!#\$%&'*+—/=?^_`{|}~,\\.-]{1,63}(?<!\\.)@(?=[^\\.])[0-9a-zA-Z-]+\\.[a-zA-Z]{2,63}(?<!\\.)\$") }

    fun validate(value: String): Boolean {
        return if (value.length > MAX_LENGTH) false
        else regex.matches(value)
    }

    fun takeAllowedLength(value: String): String {
        return value.take(MAX_LENGTH)
    }
}
