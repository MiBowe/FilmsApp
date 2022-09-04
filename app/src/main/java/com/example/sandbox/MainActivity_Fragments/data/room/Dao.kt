package com.example.sandbox.MainActivity_Fragments.data.room

import androidx.lifecycle.LiveData
import androidx.room.*
import androidx.room.Dao
import com.example.sandbox.MainActivity_Fragments.presentation.Adapter.FilmItem

@Dao
interface Dao {

    @Insert
    fun insert(publisherEntity: FilmItem?)

    @Insert
    fun insertList(publisherEntities: List<FilmItem>)

    @Delete
    fun deleteFilm(publisherEntity: FilmItem?)

    @Update
    fun updateFilm(publisherEntity: FilmItem?)


    @Query("SELECT * FROM Films")
    fun getAll(): List<FilmItem?>

    @Query("SELECT * FROM Films WHERE id = :id")
    fun getFilmByID(id: Long): FilmItem?


}