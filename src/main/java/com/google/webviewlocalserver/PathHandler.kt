package com.google.webviewlocalserver

import android.annotation.TargetApi
import android.net.Uri
import android.os.Build
import android.webkit.WebResourceRequest
import android.webkit.WebResourceResponse

interface PathHandler {
    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    fun handle(request: WebResourceRequest): WebResourceResponse? = handle(request.url)

    fun handle(url: Uri?): WebResourceResponse?
}