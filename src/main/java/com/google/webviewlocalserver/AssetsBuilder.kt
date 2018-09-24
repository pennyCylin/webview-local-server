package com.google.webviewlocalserver

import android.text.TextUtils

const val DEFAULT_ASSETS_PATH = "www"

class AssetsBuilder : ResBuilder() {

    private var mPathInAndroidLocation: String? = DEFAULT_ASSETS_PATH

    val pathInAndroidLocation: String
        get() = mPathInAndroidLocation ?: ""

    override val domain: String
        get() = super.domain

    override val urlVirtualPath: String
        get() = super.urlVirtualPath

    @Suppress("NAME_SHADOWING")
    fun setPathInAndroidLocation(pathInAndroidLocation: String): AssetsBuilder {
        var pathInAndroidLocation = pathInAndroidLocation
        if (!TextUtils.isEmpty(pathInAndroidLocation))
            while (pathInAndroidLocation.startsWith("/")) {
                pathInAndroidLocation = pathInAndroidLocation.substring(1, pathInAndroidLocation.length)
            }
        if (!TextUtils.isEmpty(pathInAndroidLocation))
            while (pathInAndroidLocation.endsWith("/")) {
                pathInAndroidLocation = pathInAndroidLocation.substring(0, pathInAndroidLocation.length - 1)
            }
        mPathInAndroidLocation = pathInAndroidLocation
        return this
    }

    override fun setDomain(domain: String): AssetsBuilder {
        return super.setDomain(domain) as AssetsBuilder
    }

    override fun setUrlVirtualPath(urlVirtualPath: String): AssetsBuilder {
        return super.setUrlVirtualPath(urlVirtualPath) as AssetsBuilder
    }

    override fun clearDomain(): AssetsBuilder {
        return super.clearDomain() as AssetsBuilder
    }

    override fun getSubDomain(): String? {
        return super.getSubDomain()
    }

    override fun setProtocol(urlProtocol: UrlProtocol, isAllowed: Boolean): AssetsBuilder {
        return super.setProtocol(urlProtocol, isAllowed) as AssetsBuilder
    }

    override fun setSubDomain(subDomain: String): AssetsBuilder {
        return super.setSubDomain(subDomain) as AssetsBuilder
    }

    override fun setRandomSubDomain(): AssetsBuilder {
        return super.setRandomSubDomain() as AssetsBuilder
    }

    override fun clearSubDomain(): AssetsBuilder {
        return super.clearSubDomain() as AssetsBuilder
    }
}
