package com.savvy.app.base.helper

class NotEmptyRule(private val errorMessage: String) : ValidateRule {
    override fun errorMessage(): String = errorMessage

    override fun validate(data: String?): Boolean = !data?.trim().isNullOrEmpty()
}
