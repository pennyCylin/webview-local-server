package com.google.webviewlocalserver

import android.webkit.WebResourceRequest
import android.webkit.WebResourceResponse

class LollipopLazyInputStream(handler: PathHandler, private val request: WebResourceRequest) : LazyInputStream(handler) {

    override fun handle(): WebResourceResponse? {
        return handler.handle(request)
    }
}
