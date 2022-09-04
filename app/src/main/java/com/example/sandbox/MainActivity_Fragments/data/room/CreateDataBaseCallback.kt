package com.example.sandbox.MainActivity_Fragments.data.room

import android.content.Context
import android.util.Log
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import java.util.concurrent.Executors

class CreateDataBaseCallback(private val context: Context): RoomDatabase.Callback() {

    override fun onCreate(db: SupportSQLiteDatabase) {
        Log.d(TAG, "DbCallback onCreate")
        Executors.newSingleThreadExecutor().execute(Runnable {
             AppDatabase.getInstance(context)?.getFilmDao()
        })
        }


        override fun onOpen(db: SupportSQLiteDatabase) {
        }

        override fun onDestructiveMigration(db: SupportSQLiteDatabase) {
        }

}