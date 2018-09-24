package com.google.webviewlocalserver

import android.net.Uri
import android.webkit.WebResourceResponse

class LegacyLazyInputStream(handler: PathHandler, private val uri: Uri) : LazyInputStream(handler) {

    override fun handle(): WebResourceResponse? {
        return handler.handle(uri)
    }
}
