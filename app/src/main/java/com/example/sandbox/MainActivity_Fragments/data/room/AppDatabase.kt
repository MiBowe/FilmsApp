package com.example.sandbox.MainActivity_Fragments.data.room

import android.content.Context
import androidx.room.Room



object AppDatabase {

    private var INSTANCE: AppDB? = null

    fun getInstance(context:Context): AppDB?{
        if (INSTANCE == null){
            synchronized(AppDatabase::class){

                INSTANCE = Room.databaseBuilder(
                    context,
                    AppDB::class.java,
                    "db-favorite_films.db"
                )
                    .addCallback(CreateDataBaseCallback(context))
                    .build()
            }
        }
        return INSTANCE
    }

    fun destroyInstance() {
        INSTANCE?.close()
        INSTANCE = null
    }
}