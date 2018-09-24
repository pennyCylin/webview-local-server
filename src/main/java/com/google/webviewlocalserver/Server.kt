package com.google.webviewlocalserver

import android.net.Uri
import android.text.TextUtils

class Server (private val builder: Uri.Builder) {

    fun getServerUri(protocol: UrlProtocol, appendToPath: String? = null): Uri {
        val builder = builder.scheme(protocol.protocol)
        if (!TextUtils.isEmpty(appendToPath)) builder.appendPath(appendToPath)
        return builder.build()
    }
}

