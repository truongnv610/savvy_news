package com.savvy.app.base.helper

interface ValidateRule {
    /* Method for your validation logic*/
    fun validate(data: String?): Boolean

    /* The error message that will be sent, if the validate method returns false*/
    fun errorMessage(): String
}
