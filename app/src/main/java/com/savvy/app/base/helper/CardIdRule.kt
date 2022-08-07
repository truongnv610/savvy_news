package com.savvy.app.base.helper

import com.savvy.app.base.extension.isValidCardId

class CardIdRule(private val errorMessage: String) : ValidateRule {
    override fun errorMessage(): String = errorMessage

    override fun validate(data: String?): Boolean = data?.isValidCardId() ?: false
}
