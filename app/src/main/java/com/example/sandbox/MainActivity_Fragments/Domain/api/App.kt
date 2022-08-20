package com.example.sandbox.MainActivity_Fragments.Domain.api

import android.app.Application
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class App: Application() {
    lateinit var api: Api

    override fun onCreate() {
        super.onCreate()
        instance = this

        initretrofit()
    }

    private fun initretrofit() {

        val client = OkHttpClient.Builder()
            .addInterceptor { chain ->
                val url = chain
                    .request()
                    .url
                    .newBuilder()
                    .build()
                var response = chain.proceed(
                    chain.request()
                        .newBuilder()
                        .url(url)
                        .addHeader("X-API-KEY", "4b68627e-c490-43f4-8860-b666b0a74084")
                        .addHeader("Content-Type", "application/json; charset=utf-8")
                        .build()
                )
                return@addInterceptor response
            }.build()


        val retrofit = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .client(client)
            .build()

        api = retrofit.create(Api::class.java)

    }

    companion object {
        const val BASE_URL = "https://kinopoiskapiunofficial.tech/api/v2.2/"

        lateinit var instance: App
            private set
    }
}