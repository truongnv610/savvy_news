package com.savvy.app.base.security

import android.webkit.WebResourceRequest
import android.webkit.WebResourceResponse
import android.webkit.WebView
import android.webkit.WebViewClient
import com.savvy.app.base.extension.getCharset
import com.savvy.app.base.extension.getMimeType
import okhttp3.OkHttpClient
import okhttp3.Request
import java.net.URL

class SecureWebViewClient(private val httpClient: OkHttpClient) : WebViewClient() {

    private var listener: OnResultListener? = null

    companion object {
        const val HEADER_CONTENT_TYPE = "Content-Type"
    }

    fun setOnLoaderListener(loaderListener: OnResultListener) {
        this.listener = loaderListener
    }

    override fun shouldInterceptRequest(
        view: WebView,
        interceptedRequest: WebResourceRequest
    ): WebResourceResponse {

        try {
            // Try to execute call with OkHttp
            val url = URL(interceptedRequest.url.toString())
            val response = httpClient.newCall(
                Request.Builder()
                    .url(url)
                    .build()
            ).execute()

            val contentType = response.header(HEADER_CONTENT_TYPE)

            // If got a contentType header
            if (contentType != null) {

                val inputStream = response.body?.byteStream()
                val mimeType = contentType.getMimeType
                val charset = contentType.getCharset

                listener?.onLoaded(url.toString())

                // Return the response
                return WebResourceResponse(mimeType, charset, inputStream)
            }
        } catch (ex: Exception) {
            listener?.onPreventedLoading(ex.message ?: ex.javaClass.simpleName)
        }

        // Very important to return empty WebResourceResponse like this. If you return just null,
        // the request will still be executed!
        return WebResourceResponse(null, null, null)
    }

    interface OnResultListener {
        fun onLoaded(url: String)
        fun onPreventedLoading(reason: String)
    }
}
