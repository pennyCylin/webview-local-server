package com.google.webviewlocalserver

import android.annotation.TargetApi
import android.content.Context
import android.net.Uri
import android.os.Build
import android.text.TextUtils
import android.util.Log
import android.webkit.WebResourceRequest
import android.webkit.WebResourceResponse
import com.google.webviewlocalserver.third_party.android.UriMatcher
import com.google.webviewlocalserver.third_party.chromium.AndroidProtocolHandler
import java.net.URLEncoder
import java.util.*

class WebViewLocalServer internal constructor(private val protocolHandler: AndroidProtocolHandler) {

    private val uriMatcher: UriMatcher = UriMatcher(null)
    private val authority: String = UUID.randomUUID().toString() + "." + knownUnusedAuthority
    private var pathHandler: PathHandler? = null

    constructor(context: Context) : this(AndroidProtocolHandler(context.applicationContext))

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    fun shouldInterceptRequest(request: WebResourceRequest): WebResourceResponse? {
        synchronized(uriMatcher) {
            pathHandler = uriMatcher.match(request.url) as PathHandler
        }
        return if (pathHandler == null) null else pathHandler?.handle(request)
    }

    fun shouldInterceptRequest(url: String): WebResourceResponse? {
        val uri = parseAndVerifyUrl(url)
        if (uri != null) {
            synchronized(uriMatcher) {
                pathHandler = uriMatcher.match(uri) as PathHandler
            }
        }
        return pathHandler?.handle(uri)
    }

    private fun register(uri: Uri, handler: PathHandler?) {
        synchronized(uriMatcher) {
            uriMatcher.addURI(uri.scheme, uri.authority, uri.path, handler)
        }
    }

    fun createHost(builder: Builder): Server {
        var domain = builder.domain
        if (!TextUtils.isEmpty(builder.getSubDomain()))
            domain = builder.getSubDomain() + "." + domain

        val uriBuilder = Uri.Builder()
        uriBuilder.scheme(UrlProtocol.HTTPS.protocol)
        var encodedAuthority = domain
        try {
            encodedAuthority = URLEncoder.encode(domain, "UTF-8")
        } catch (e: Exception) {

        }

        if (builder.getPort() > -1) encodedAuthority += ":" + builder.getPort().toString()
        uriBuilder.encodedAuthority(encodedAuthority)
        uriBuilder.path(builder.urlVirtualPath)

        if (builder is AssetsBuilder && builder.pathInAndroidLocation.indexOf('*') != -1) {
            throw IllegalArgumentException("assetPath cannot contain the '*' character.")
        }
        if (builder.urlVirtualPath.indexOf('*') != -1) {
            throw IllegalArgumentException("virtualAssetPath cannot contain the '*' character.")
        }

        pathHandler = if (builder is AssetsBuilder) {
            AssetPathHandler(protocolHandler, builder.pathInAndroidLocation, builder.urlVirtualPath)
        } else {
            ResourcesPathHandler(protocolHandler)
        }

        for (mapEntry in builder.isAllowed.entries) {
            if (mapEntry.value) register(Uri.withAppendedPath(uriBuilder.scheme(mapEntry.key.protocol).build(), "**"), pathHandler)
        }

        return Server(uriBuilder)
    }

    companion object {
        private val TAG = "WebViewAssetServer"
        private val knownUnusedAuthority = "androidplatform.net"

        private fun parseAndVerifyUrl(url: String?): Uri? {
            if (url == null) {
                return null
            }
            val uri = Uri.parse(url)
            if (uri == null) {
                Log.e(TAG, "Malformed URL: $url")
                return null
            }
            val path = uri.path
            if (path == null || path.isEmpty()) {
                Log.e(TAG, "URL does not have a path: $url")
                return null
            }
            return uri
        }
    }
}
