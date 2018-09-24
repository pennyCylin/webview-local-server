package com.google.webviewlocalserver

import android.text.TextUtils
import java.util.*

const val DEFAULT_DOMAIN = "androidplatform.net"

@Suppress("LeakingThis")
open class Builder {

    private var mDomain = DEFAULT_DOMAIN
    private var mSubDomain: String? = null
    private var mUrlVirtualPath = ""
    private val mIsAllowed: MutableMap<UrlProtocol, Boolean>
    private var mPort = -1

    open val domain: String
        get() = if (TextUtils.isEmpty(mDomain)) DEFAULT_DOMAIN else if (mDomain == "**NONE") "" else mDomain

    open val urlVirtualPath: String
        get() {
            if (TextUtils.isEmpty(mUrlVirtualPath)) return ""
            return if (TextUtils.isEmpty(mUrlVirtualPath)) "" else mUrlVirtualPath
        }

    val isAllowed: Map<UrlProtocol, Boolean>
        get() = mIsAllowed

    init {
        mIsAllowed = HashMap()
        for (urlProtocol in UrlProtocol.values()) {
            mIsAllowed[urlProtocol] = true
        }
        setRandomSubDomain()
    }

    @Suppress("NAME_SHADOWING")
    open fun setDomain(domain: String): Builder {
        var domain = domain
        if (!TextUtils.isEmpty(domain))
            while (domain.startsWith("/")) {
                domain = domain.substring(1, domain.length)
            }
        if (!TextUtils.isEmpty(domain))
            while (domain.endsWith("/")) {
                domain = domain.substring(0, domain.length - 1)
            }
        mDomain = domain
        return this
    }

    @Suppress("NAME_SHADOWING")
    open fun setUrlVirtualPath(urlVirtualPath: String): Builder {
        var urlVirtualPath = urlVirtualPath
        if (!TextUtils.isEmpty(urlVirtualPath))
            while (urlVirtualPath.startsWith("/")) {
                urlVirtualPath = urlVirtualPath.substring(1, urlVirtualPath.length)
            }
        if (!TextUtils.isEmpty(urlVirtualPath))
            while (urlVirtualPath.endsWith("/")) {
                urlVirtualPath = urlVirtualPath.substring(0, urlVirtualPath.length - 1)
            }
        mUrlVirtualPath = urlVirtualPath
        return this
    }

    fun setPort(port: Int): Builder {
        mPort = port
        return this
    }

    fun getPort(): Int {
        return mPort
    }

    open fun getSubDomain(): String? {
        return mSubDomain
    }

    open fun clearDomain(): Builder {
        mDomain = "**NONE"
        return this
    }

    open fun setProtocol(urlProtocol: UrlProtocol, isAllowed: Boolean): Builder {
        mIsAllowed[urlProtocol] = isAllowed
        return this
    }

    open fun setSubDomain(subDomain: String): Builder {
        mSubDomain = subDomain
        return this
    }

    open fun setRandomSubDomain(): Builder {
        mSubDomain = UUID.randomUUID().toString()
        return this
    }

    open fun clearSubDomain(): Builder {
        mSubDomain = null
        return this
    }
}