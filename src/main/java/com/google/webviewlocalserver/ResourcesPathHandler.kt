package com.google.webviewlocalserver

import android.net.Uri
import android.os.Build
import android.util.Log
import android.webkit.WebResourceResponse
import com.google.webviewlocalserver.third_party.chromium.AndroidProtocolHandler
import java.net.URLConnection

private val TAG = "ResourcesPathHandler"

class ResourcesPathHandler(private val protocolHandler: AndroidProtocolHandler): PathHandler {
    override fun handle(url: Uri?): WebResourceResponse? {
        val stream = protocolHandler.openResource(url)
        var mimeType: String? = null
        try {
            mimeType = URLConnection.guessContentTypeFromStream(stream)
        } catch (ex: Exception) {
            Log.e(TAG, "Unable to get mime type $url")
        }

        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            WebResourceResponse(mimeType, null, 200, "OK", null, stream)
        } else {
            WebResourceResponse(mimeType, null, stream)
        }
    }
}