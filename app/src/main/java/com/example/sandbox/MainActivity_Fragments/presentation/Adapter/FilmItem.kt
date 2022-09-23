package com.example.sandbox.MainActivity_Fragments.presentation.Adapter
import androidx.room.Entity
import java.io.Serializable

@Entity(tableName = "films", primaryKeys = ["id", "idDB"])
data class FilmItem(
    val poster: String?,
    val posterToolbar: String?,
    val id: Int,
    val title: String?,
    val subtitle: String?,
    val idDB: Int = 0,
    var isFavorite: Boolean = false,
    var pageNumber: Int
):Serializable
