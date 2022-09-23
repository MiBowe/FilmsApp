package com.example.sandbox.MainActivity_Fragments

import android.app.Application
import android.util.Log
import androidx.room.Room
import com.example.sandbox.MainActivity_Fragments.data.api.Api
import com.example.sandbox.MainActivity_Fragments.data.room.AppDB
import com.example.sandbox.MainActivity_Fragments.data.room.AppDatabase
import com.example.sandbox.MainActivity_Fragments.data.room.CreateDataBaseCallback
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

private const val DATABASE_NAME = "DataBaseName"

class App: Application() {

    lateinit var api: Api
    var appDB: AppDB? = null

    override fun onCreate() {
        super.onCreate()
        instance = this
        initretrofit()
        Log.d("TEST -------------------------------->>","onCreate: App")

        Log.d("TEST -------------------------------->>","after onCreate: App")
        initAppDB()
        }

    private fun initretrofit() {

        val client = OkHttpClient.Builder()
            .addInterceptor(HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY))
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
                        .addHeader("X-API-KEY", "351310e8-30bd-42bc-ab70-4a8037b97d76")
                        .addHeader("Content-Type", "application/json")
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

    private fun initAppDB(){
        Room.databaseBuilder(
            this,
            AppDB::class.java,
            DATABASE_NAME
        )
            .allowMainThreadQueries()
            .addCallback(CreateDataBaseCallback(this))
            .build()
        appDB = AppDatabase.getInstance(baseContext)
    }
}