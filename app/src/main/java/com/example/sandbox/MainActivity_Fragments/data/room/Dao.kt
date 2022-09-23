package com.example.sandbox.MainActivity_Fragments.data.room

import androidx.room.*
import androidx.room.Dao
import com.example.sandbox.MainActivity_Fragments.presentation.Adapter.FilmItem

@Dao
interface Dao {

    @Insert
    fun insert(publisherEntity: FilmItem?)

    @Insert
    fun insertFilms(films: List<FilmItem>)

    @Delete
    fun deleteFilm(publisherEntity: FilmItem?)

    @Update
    fun updateFilm(publisherEntity: FilmItem?)

    @Query("SELECT * FROM films")
    fun getAll(): List<FilmItem?>

    @Query("SELECT * FROM films WHERE id  = :id")
    fun getFilmByID(id: Int): FilmItem

    @Query("SELECT * FROM films WHERE pageNumber = :page")
    fun getFilmsByPage(page: Int): List<FilmItem>



    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun save(films: List<FilmItem>)


    @Query("DELETE FROM films WHERE id IS NULL OR id = :id")
    suspend fun clear(id: Int?)


    @Transaction
    suspend fun refresh(films: List<FilmItem>){
        save(films)
    }

    suspend fun save(filmItem: FilmItem){
        save(listOf(filmItem))
    }
}