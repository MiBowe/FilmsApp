package com.example.sandbox.MainActivity_Fragments.Adapter
import java.io.Serializable

data class FilmItem (var poster: Int,var details : Int, var title : String, var subtitle : String, var isFavorite : Boolean) : Serializable