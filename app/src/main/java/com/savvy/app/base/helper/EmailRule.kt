package com.savvy.app.base.helper

import com.savvy.app.base.extension.isValidEmail

class EmailRule(private val errorMessage: String) : ValidateRule {
    override fun errorMessage(): String = errorMessage

    override fun validate(data: String?): Boolean = data?.isValidEmail() ?: false
}
