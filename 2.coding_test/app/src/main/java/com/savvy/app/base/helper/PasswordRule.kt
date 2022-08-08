package com.savvy.app.base.helper

import com.savvy.app.base.extension.isValidPassword

class PasswordRule(private val errorMessage: String) : ValidateRule {
    override fun errorMessage(): String = errorMessage

    override fun validate(data: String?): Boolean = data?.isValidPassword() ?: false
}
