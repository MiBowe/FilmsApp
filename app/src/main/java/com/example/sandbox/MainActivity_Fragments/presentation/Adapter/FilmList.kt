package com.example.sandbox.MainActivity_Fragments.presentation.Adapter
import com.example.sandbox.R
class FilmList {
    companion object{
        val filmlist = listOf(
            FilmItem(R.drawable.it_tool,R.drawable.it_poster,R.string.it_details,"Оно", "Ужасы", false),
            FilmItem(R.drawable.it_tool,R.drawable.it2_poster,R.string.it2_details,"Оно 2","Ужасы", false,),
            FilmItem(R.drawable.avatar_tool,R.drawable.avatar_poster,R.string.avatar_details,"Аватар","Фантастика", false,),
            FilmItem(R.drawable.gwg_tool,R.drawable.gwg_poster,R.string.gwg_details,"Парни со стволами","Комедия", false),
            FilmItem(R.drawable.astral_tool,R.drawable.astral_poster,R.string.astral_details,"Астрал","Ужасы", false),
            FilmItem(R.drawable.avengers_tool,R.drawable.avengers_poster,R.string.avengers_details,"Мстители","Фантастика", false),
        )
    }
}