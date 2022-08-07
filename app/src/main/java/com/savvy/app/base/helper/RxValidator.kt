package com.savvy.app.base.helper

import android.widget.EditText
import com.google.android.material.textfield.TextInputLayout
import io.reactivex.rxjava3.core.Observable

open class RxValidator(private val inputLayout: TextInputLayout) : Validator() {
    /**
     * It`s return new validation observable, that start validate, after your subscription
     * @return new validation observable
     * @param shouldCheckCurrentText default false: if true it will validate current text in EditText when subscribe then emit event
     */
    fun asObservable(shouldCheckCurrentText: Boolean = false): Observable<Boolean> {
        return createObservable(editText, shouldCheckCurrentText).map {
            validate(it, onSuccess = {
                inputLayout.error = null
            }, onError = { message ->
                inputLayout.error = message
            })
        }
    }

    protected open val editText by lazy {
        inputLayout.editText ?: throw NullPointerException(
            "Your ${TextInputLayout::class.java.simpleName} should has EditText as child"
        )
    }

    companion object {
        /**
         * @return represent EditText TextWatcher as Observable<String>, that emmit elements by onTextChanged
         * @param shouldEmitCurrentText default false: if true it will emit the current text in EditText when subscribe
         */
        fun createObservable(editText: EditText, shouldEmitCurrentText: Boolean): Observable<String> {
            return Observable.create {
                if (shouldEmitCurrentText) {
                    it.onNext(editText.text.toString())
                }
                editText.addTextChangedListener(object : SimpleTextWatcher() {
                    override fun onTextChanged(s: CharSequence?, p1: Int, p2: Int, p3: Int) {
                        it.onNext(s.toString())
                    }
                })
            }
        }
    }
}
