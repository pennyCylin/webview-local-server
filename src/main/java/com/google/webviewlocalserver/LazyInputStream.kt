package com.google.webviewlocalserver

import android.webkit.WebResourceResponse

import java.io.IOException
import java.io.InputStream

abstract class LazyInputStream(internal val handler: PathHandler) : InputStream() {
    private var response: WebResourceResponse? = null

    private val inputStream: InputStream?
        get() {
            if (response == null) {
                response = handle()
            }
            return response?.data
        }

    protected abstract fun handle(): WebResourceResponse?

    @Throws(IOException::class)
    override fun available(): Int {
        return inputStream?.available() ?: 0
    }

    @Throws(IOException::class)
    override fun read(): Int {
        return inputStream?.read() ?: -1
    }

    @Throws(IOException::class)
    override fun read(b: ByteArray): Int {
        return inputStream?.read(b) ?: -1
    }

    @Throws(IOException::class)
    override fun read(b: ByteArray, off: Int, len: Int): Int {
        return inputStream?.read(b, off, len) ?: -1
    }

    @Throws(IOException::class)
    override fun skip(n: Long): Long {
        return inputStream?.skip(n) ?: 0
    }
}
