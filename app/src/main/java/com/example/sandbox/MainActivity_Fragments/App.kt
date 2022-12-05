package com.example.sandbox.MainActivity_Fragments

import android.app.Application
import android.util.JsonToken
import android.util.Log
import androidx.room.Room
import com.example.sandbox.MainActivity_Fragments.data.api.Api
import com.example.sandbox.MainActivity_Fragments.data.room.AppDB
import com.example.sandbox.MainActivity_Fragments.data.room.AppDatabase
import com.example.sandbox.MainActivity_Fragments.data.room.CreateDataBaseCallback
import com.google.firebase.messaging.FirebaseMessaging
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import kotlin.coroutines.suspendCoroutine

private const val DATABASE_NAME = "DataBaseName"

class App: Application() {

    lateinit var api_films: Api
    lateinit var api_notification: Api
    var token: String = "a"
    var appDB: AppDB? = null

    override fun onCreate() {
        super.onCreate()

        FirebaseMessaging.getInstance().token.addOnCompleteListener { task ->
            if (!task.isSuccessful){
                return@addOnCompleteListener
            }

            token = task.result
            Log.d("TOKEN __________________________________________________________________________________>", "Token -> $token")

            initNotification(token = token)
        }


        instance = this

        Log.d("TEST -------------------------------->>","onCreate: App")


        Log.d("TEST -------------------------------->>","after onCreate: App")
        initAppDB()
        initretrofit()

        }

    private fun initretrofit() {

        val client = OkHttpClient.Builder()
            .connectTimeout(15, TimeUnit.SECONDS)
            .readTimeout(15, TimeUnit.SECONDS)
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

        api_films = retrofit.create(Api::class.java)

    }
    private fun initNotification(token: String){

     val clientNotif = OkHttpClient.Builder()
         .connectTimeout(15, TimeUnit.SECONDS)
         .readTimeout(15,TimeUnit.SECONDS)
         .addInterceptor(HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY))
         .addInterceptor { chain ->
             val url = chain
                 .request()
                 .url
                 .newBuilder()
                 .build()
             val response = chain.proceed(
                 chain.request()
                     .newBuilder()
                     .url(url)
                     .addHeader("Content-Type", "application/json")
                     .addHeader("Authorization", "key=AAAA1iSsiVk:APA91bE7SCeWI1LYSt3H1O-xUqaAOTyf4Rfq8IA10pDNRoxCuSY_4ziHbzUFwMgyZ3hjDgDxGGkr7IMGFdT8kGw3Xr27NR9FHjzXNaIqndJTxUiGFsv8tv6WjkjBBgEJeyRW9n-bA0wT")
                     .build()
             )
             return@addInterceptor response
         }.build()
        val retrofitNotif = Retrofit.Builder()
            .baseUrl(NOTIF_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .client(clientNotif)
            .build()

        api_notification = retrofitNotif.create(Api::class.java)
    }

    /*private fun initNotification(token: String){

        val logging = HttpLoggingInterceptor()
        logging.level = (HttpLoggingInterceptor.Level.BODY)

        val client_notif = OkHttpClient.Builder()
            .connectTimeout(15, TimeUnit.SECONDS)
            .readTimeout(15, TimeUnit.SECONDS)
            .addInterceptor{ chain ->
            val url = chain.request()
            val response = url.newBuilder()
                .header("Authorization", "key=$token")
                val request = response.build()
                chain.proceed(request)
            }
            .addInterceptor(logging)
            .build()

        val retrofitNotif = Retrofit.Builder()
            .baseUrl(NOTIF_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .client(client_notif)
            .build()

        api_notification = retrofitNotif.create(Api::class.java)
        Log.d("TEST -------------------------------->>","onCreate: TOKEN ____ $token")
    }*/

    companion object {
        const val BASE_URL = "https://kinopoiskapiunofficial.tech/api/v2.2/"
        const val NOTIF_URL = "https://fcm.googleapis.com/"

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