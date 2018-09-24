package com.google.webviewlocalserver

import android.annotation.TargetApi
import android.net.Uri
import android.os.Build
import android.util.Log
import android.webkit.WebResourceRequest
import android.webkit.WebResourceResponse
import com.google.webviewlocalserver.third_party.chromium.AndroidProtocolHandler
import java.io.IOException
import java.net.URLConnection

private val TAG = "AssetPathHandler"

class AssetPathHandler(private val protocolHandler: AndroidProtocolHandler, private val assetPath: String, private val virtualAssetPath: String = "/assets") : PathHandler {

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    override fun handle(request: WebResourceRequest): WebResourceResponse? {
        return handle(request.url)
    }

    override fun handle(url: Uri?): WebResourceResponse? {
        var path = url?.path ?: return null
        if (path.startsWith("/")) path = path.replaceFirst("/".toRegex(), "")
        if (path.startsWith(virtualAssetPath)) path = path.replaceFirst(virtualAssetPath.toRegex(), "")
        path = assetPath + (if (path.startsWith("/")) "" else "/") + path

        val stream = try {
            protocolHandler.openAsset(path)
        } catch (e: IOException) {
            Log.e(TAG, "Unable to open asset URL: $url")
            return null
        }

        var mimeType: String? = null
        try {
            mimeType = URLConnection.guessContentTypeFromName(path)
            if (mimeType == null) mimeType = URLConnection.guessContentTypeFromStream(stream)
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