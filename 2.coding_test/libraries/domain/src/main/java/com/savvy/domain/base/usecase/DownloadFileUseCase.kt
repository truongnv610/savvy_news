package com.savvy.domain.base.usecase

import android.app.DownloadManager
import android.content.Context
import android.net.Uri
import android.os.Environment
import com.savvy.domain.R
import com.savvy.domain.base.extension.getMimeTypeForUrlOrFileName
import dagger.hilt.android.qualifiers.ApplicationContext
import io.reactivex.rxjava3.core.Completable
import javax.inject.Inject

class DownloadFileUseCase @Inject constructor(
    @ApplicationContext private val context: Context,
    private val downloadManager: DownloadManager
) {
    fun execute(url: String , fileName : String): Completable {
        return Completable.create { emitter ->
            val request = DownloadManager.Request(Uri.parse(url)).apply {
                setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_ONLY_COMPLETION)
                setTitle(fileName)
                setDescription(context.getString(R.string.downloading))
                setDestinationInExternalPublicDir(Environment.DIRECTORY_DOWNLOADS, fileName)
                setMimeType(fileName.getMimeTypeForUrlOrFileName())
                setRequiresCharging(false)
                setAllowedOverMetered(true)
                setAllowedOverRoaming(true)
            }
            downloadManager.enqueue(request)
            emitter.onComplete()
        }
    }
}