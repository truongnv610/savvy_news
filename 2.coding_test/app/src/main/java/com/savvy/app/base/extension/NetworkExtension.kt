package com.savvy.app.base.extension

import okhttp3.Request
import retrofit2.Invocation

fun <T : Annotation> Request.hasAnnotation(annotationClass: Class<T>): Boolean {
    return this.tag(Invocation::class.java)?.method()?.getAnnotation(annotationClass) != null
}
