package com.savvy.app.base.extension

import android.content.Context
import android.graphics.Bitmap
import android.graphics.drawable.Drawable
import android.net.Uri
import android.util.Base64
import android.widget.ImageView
import androidx.annotation.DrawableRes
import androidx.core.content.ContextCompat
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.load.resource.bitmap.DownsampleStrategy
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions.withCrossFade
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target
import com.savvy.app.R
import com.savvy.app.base.GlideApp
import timber.log.Timber

fun ImageView.loadUrl(context: Context, url: String, cropCircle: Boolean = false) {
    GlideApp.with(context)
        .load(url)
        .apply {
            if (cropCircle) circleCrop()
        }
        .transition(withCrossFade())
        .error(ContextCompat.getDrawable(context, R.drawable.ic_placeholder))
        .into(this)
}

fun ImageView.loadUrlCenterCrop(context: Context, url: String) {
    GlideApp.with(context)
        .load(url)
        .centerCrop()
        .transition(withCrossFade())
        .into(this)
}

fun ImageView.loadUrlCenterInside(context: Context, url: String) {
    GlideApp.with(context)
        .load(url)
        .downsample(DownsampleStrategy.CENTER_INSIDE)
        .centerInside()
        .transition(withCrossFade())
        .into(this)
}

fun ImageView.loadUrlFitCenter(context: Context, url: String) {
    GlideApp.with(context)
        .load(url)
        .fitCenter()
        .transition(withCrossFade())
        .into(this)
}

fun ImageView.loadUrlWithFallback(
    context: Context,
    imageUrl: String,
    fallbackUrl: String = imageUrl,
    cropCircle: Boolean = false,
    @DrawableRes errorDrawable: Int = R.drawable.ic_placeholder,
    predicate: (Boolean) -> Unit = {}
) {
    val isSameUrl = imageUrl == fallbackUrl
    GlideApp.with(context)
        .load(imageUrl)
        .apply {
            if (cropCircle) circleCrop()
        }
        .downsample(DownsampleStrategy.CENTER_INSIDE)
        .centerInside()
        .apply {
            when {
                isSameUrl -> error(errorDrawable)
                else -> error(
                    GlideApp.with(context)
                        .load(fallbackUrl)
                        .error(errorDrawable)
                        .listener(object : RequestListener<Drawable?> {
                            override fun onResourceReady(
                                resource: Drawable?,
                                model: Any?,
                                target: Target<Drawable?>?,
                                dataSource: DataSource?,
                                isFirstResource: Boolean
                            ): Boolean {
                                Timber.d("Fallback url loaded.")
                                predicate(true)
                                return false
                            }

                            override fun onLoadFailed(
                                e: GlideException?,
                                model: Any?,
                                target: Target<Drawable?>?,
                                isFirstResource: Boolean
                            ): Boolean {
                                predicate(false)
                                return false
                            }
                        })
                )
            }
        }
        .transition(withCrossFade())
        .listener(object : RequestListener<Drawable?> {
            override fun onResourceReady(
                resource: Drawable?,
                model: Any?,
                target: Target<Drawable?>?,
                dataSource: DataSource?,
                isFirstResource: Boolean
            ): Boolean {
                predicate(true)
                return false
            }

            override fun onLoadFailed(
                e: GlideException?,
                model: Any?,
                target: Target<Drawable?>?,
                isFirstResource: Boolean
            ): Boolean {
                if (isSameUrl) predicate(false)
                return false
            }
        })
        .into(this)
}

fun ImageView.loadUrlCenterCrop(context: Context, imageUrl: String, predicate: (Boolean) -> Unit) {
    GlideApp.with(context)
        .load(imageUrl)
        .centerCrop()
        .listener(object : RequestListener<Drawable?> {
            override fun onLoadFailed(
                e: GlideException?,
                model: Any?,
                target: Target<Drawable?>?,
                isFirstResource: Boolean
            ): Boolean {
                predicate(false)
                return false
            }

            override fun onResourceReady(
                resource: Drawable?,
                model: Any?,
                target: Target<Drawable?>?,
                dataSource: DataSource?,
                isFirstResource: Boolean
            ): Boolean {
                predicate(true)
                return false
            }
        })
        .transition(withCrossFade())
        .into(this)
}

fun ImageView.loadUri(context: Context, imageUri: Uri, predicate: (Boolean) -> Unit = {}) {
    GlideApp.with(context)
        .load(imageUri)
        .centerCrop()
        .listener(object : RequestListener<Drawable?> {
            override fun onLoadFailed(
                e: GlideException?,
                model: Any?,
                target: Target<Drawable?>?,
                isFirstResource: Boolean
            ): Boolean {
                predicate(false)
                return false
            }

            override fun onResourceReady(
                resource: Drawable?,
                model: Any?,
                target: Target<Drawable?>?,
                dataSource: DataSource?,
                isFirstResource: Boolean
            ): Boolean {
                predicate(true)
                return false
            }
        })
        .transition(withCrossFade())
        .into(this)
}

fun ImageView.loadUrlWithPlaceHolderAndError(
    context: Context,
    url: String,
    cropCircle: Boolean,
    @DrawableRes placeholder: Int,
    @DrawableRes error: Int
) {
    GlideApp.with(context)
        .load(url)
        .fitCenter()
        .placeholder(placeholder)
        .error(error)
        .apply {
            if (cropCircle) circleCrop()
        }
        .transition(withCrossFade())
        .into(this)
}

fun ImageView.loadUrlWithPlaceHolderAndError(
    context: Context,
    url: String,
    cropCircle: Boolean,
    placeholderDrawable: Drawable,
    @DrawableRes error: Int
) {
    GlideApp.with(context)
        .load(url)
        .fitCenter()
        .placeholder(placeholderDrawable)
        .error(error)
        .apply {
            if (cropCircle) circleCrop()
        }
        .transition(withCrossFade())
        .into(this)
}

fun ImageView.loadUriWithPlaceHolderAndError(
    context: Context,
    uri: Uri,
    cropCircle: Boolean,
    @DrawableRes placeholder: Int,
    @DrawableRes error: Int
) {
    GlideApp.with(context)
        .load(uri)
        .fitCenter()
        .placeholder(placeholder)
        .error(error)
        .apply {
            if (cropCircle) circleCrop()
        }
        .transition(withCrossFade())
        .into(this)
}

fun ImageView.loadImage(image: Any) {
    when (image) {
        is String -> this.loadUrlCenterCrop(context, image)
        is Int -> this.setImageResource(image)
        is Bitmap -> this.setImageBitmap(image)
    }
}

fun ImageView.loadBase64(base64: String) {
    GlideApp.with(context)
        .load(Base64.decode(base64, Base64.DEFAULT))
        .transition(withCrossFade())
        .error(ContextCompat.getDrawable(context, R.drawable.ic_placeholder))
        .into(this)
}

fun ImageView.loadYoutubeThumbnail(context: Context, youtubeId: String) {
    loadUrl(context, "https://img.youtube.com/vi/$youtubeId/0.jpg")
}
