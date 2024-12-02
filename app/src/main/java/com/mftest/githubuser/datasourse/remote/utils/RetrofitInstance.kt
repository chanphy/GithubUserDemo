package com.mftest.githubuser.datasourse.remote.utils

import com.mftest.githubuser.datasourse.remote.interfaces.GithubApi
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import kotlin.jvm.java

val client = OkHttpClient.Builder()
//    .addInterceptor(AuthInterceptor("PERSONAL_ACCESS_TOKEN"))
    .build()

object RetrofitInstance {
    private const val BASE_URL = "https://api.github.com/"

    val api: GithubApi by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(client)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(GithubApi::class.java)
    }
}
