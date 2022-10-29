package com.faheem.readers.data.client

import com.faheem.readers.BuildConfig
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class ApiClient {
    private val logger: HttpLoggingInterceptor
        get() {
            val loggingInterceptor = HttpLoggingInterceptor()
            if (BuildConfig.DEBUG) {
                loggingInterceptor.apply { level = HttpLoggingInterceptor.Level.BODY }
            } else {
                HttpLoggingInterceptor.Level.NONE
            }
            return loggingInterceptor
        }

    private val authInterceptor: Interceptor = Interceptor { chain ->
        var original = chain.request()
        val apiKey = "7EkJGIlnSmDczB6w9nz4ehAejAZ6XZYP"
        val url = original.url.newBuilder().addQueryParameter("api-key", apiKey).build()
        original = original.newBuilder().url(url).build()
        chain.proceed(original)
    }

    fun build(baseUrl: String): Retrofit = Retrofit.Builder()
        .baseUrl(baseUrl)
        .client(okHttpClient)
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    private val okHttpClient: OkHttpClient
        get() {
            val okHttpBuilder: OkHttpClient.Builder = OkHttpClient.Builder()

            okHttpBuilder.addInterceptor(logger)
            okHttpBuilder.addInterceptor(authInterceptor)
            return okHttpBuilder.build()
        }
}