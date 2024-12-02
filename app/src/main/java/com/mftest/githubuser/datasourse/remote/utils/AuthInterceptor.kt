package com.mftest.githubuser.datasourse.remote.utils

import okhttp3.Interceptor
import okhttp3.Response

class AuthInterceptor(private val token: String) : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request().newBuilder()
            .addHeader("Authorization", "token $token")
            .build()
        return chain.proceed(request)
    }
}