package com.google.webviewlocalserver

open class ResBuilder : Builder() {

    override val domain: String
        get() = super.domain

    override val urlVirtualPath: String
        get() = super.urlVirtualPath

    override fun setDomain(domain: String): ResBuilder {
        return super.setDomain(domain) as ResBuilder
    }

    override fun setUrlVirtualPath(urlVirtualPath: String): ResBuilder {
        return super.setUrlVirtualPath(urlVirtualPath) as ResBuilder
    }

    override fun getSubDomain(): String? {
        return super.getSubDomain()
    }

    override fun clearDomain(): ResBuilder {
        return super.clearDomain() as ResBuilder
    }

    override fun setProtocol(urlProtocol: UrlProtocol, isAllowed: Boolean): ResBuilder {
        return super.setProtocol(urlProtocol, isAllowed) as ResBuilder
    }

    override fun setSubDomain(subDomain: String): ResBuilder {
        return super.setSubDomain(subDomain) as ResBuilder
    }

    override fun setRandomSubDomain(): ResBuilder {
        return super.setRandomSubDomain() as ResBuilder
    }

    override fun clearSubDomain(): ResBuilder {
        return super.clearSubDomain() as ResBuilder
    }
}
