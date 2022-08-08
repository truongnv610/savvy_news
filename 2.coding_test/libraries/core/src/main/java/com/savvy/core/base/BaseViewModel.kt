package com.savvy.core.base

import androidx.annotation.StringRes
import androidx.lifecycle.ViewModel
import com.google.gson.Gson
import com.savvy.core.base.model.ErrorResponse
import com.savvy.data.base.helper.BuildConfigHelper
import com.zhuinden.eventemitter.EventEmitter
import io.reactivex.rxjava3.disposables.CompositeDisposable
import retrofit2.HttpException
import java.net.HttpURLConnection

abstract class BaseViewModel : ViewModel() {
    protected val disposables by lazy { CompositeDisposable() }
    val loadingLiveEvent by lazy { EventEmitter<Boolean>() }
    val errorLiveEvent by lazy { EventEmitter<Int>() }
    val errorMessageLiveEvent by lazy { EventEmitter<String>() }

    val apiKey by lazy { getApiKey(BuildConfigHelper.FLAVOR) }

    init {
        System.loadLibrary("native-cert")
    }

    private external fun getApiKey(flavor: String): String

    override fun onCleared() {
        disposables.clear()
        super.onCleared()
    }

    protected fun showLoading() {
        loadingLiveEvent.emit(true)
    }

    protected fun hideLoading() {
        loadingLiveEvent.emit(false)
    }

    protected fun showError(@StringRes error: Int) {
        errorLiveEvent.emit(error)
    }

    protected fun showError(errorMessage: String) {
        errorMessageLiveEvent.emit(errorMessage)
    }

    protected fun Throwable.toErrorResponse(): ErrorResponse? = when {
        this is HttpException && this.code() in arrayOf(
            HttpURLConnection.HTTP_INTERNAL_ERROR,
            HttpURLConnection.HTTP_BAD_REQUEST,
            HttpURLConnection.HTTP_FORBIDDEN,
        ) -> Gson().fromJson(this.response()?.errorBody()?.string(), ErrorResponse::class.java)
        else -> null
    }
}