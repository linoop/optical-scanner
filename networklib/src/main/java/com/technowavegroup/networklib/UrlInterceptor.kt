package com.technowavegroup.networklib

import okhttp3.HttpUrl
import okhttp3.HttpUrl.Companion.toHttpUrlOrNull
import okhttp3.Interceptor
import okhttp3.Response
import java.io.IOException

class UrlInterceptor(private val baseUrl: String) : Interceptor {
    @Throws(IOException::class)
    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request().newBuilder()
        val newUrl = chain.request().url.toString().replace("https://localhost/", baseUrl)
            .toHttpUrlOrNull()
        request.url(newUrl ?: chain.request().url)
        return chain.proceed(request.build())
    }
}