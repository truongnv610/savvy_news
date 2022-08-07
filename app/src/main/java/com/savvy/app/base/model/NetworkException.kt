package com.savvy.app.base.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import java.io.IOException

sealed class NetworkException : Parcelable {

    @Parcelize
    data class ResponseError(val code: Int, val message: String?) : NetworkException(), Parcelable

    @Parcelize
    data class ConnectionError(val exception: IOException) : NetworkException(), Parcelable
}
