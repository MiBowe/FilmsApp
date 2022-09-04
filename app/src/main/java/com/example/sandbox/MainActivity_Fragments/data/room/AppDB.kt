package com.example.sandbox.MainActivity_Fragments.data.room

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.sandbox.MainActivity_Fragments.presentation.Adapter.FilmItem

const val TAG = "AppDb"

@Database(
    entities = [FilmItem::class],
    version = 1
)
abstract class AppDB: RoomDatabase() {
    abstract fun getFilmDao(): Dao
}