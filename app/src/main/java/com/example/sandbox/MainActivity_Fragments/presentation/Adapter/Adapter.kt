package com.example.sandbox.MainActivity_Fragments.presentation.Adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.sandbox.R

class Adapter (private val films: List<FilmItem>, private val listener: Listener) : RecyclerView.Adapter<Adapter.FilmsViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FilmsViewHolder =

        FilmsViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.card_layout,parent,false))


    override fun onBindViewHolder(holder: FilmsViewHolder, position: Int) {

        holder.bind(films[position],listener,position)
    }

    override fun getItemCount(): Int = films.size

    class FilmsViewHolder(view: View):RecyclerView.ViewHolder(view) {

        private val poster: ImageView = view.findViewById(R.id.card_poster)
        private val title: TextView = view.findViewById(R.id.card_title)
        private val subtitle: TextView = view.findViewById(R.id.card_subtitle)
        private val favoriteClick: CheckBox = view.findViewById(R.id.favorite_click)


        fun bind(item: FilmItem, listener: Listener,position: Int){
            title.text = item.title
            subtitle.text = item.subtitle


            Glide.with(poster.context)
                .load(item.poster)
                .override(poster.resources.getDimensionPixelSize(R.dimen.poster_size))
                .centerCrop()
                .into(poster)

            itemView.setOnClickListener{
                listener.onClick(item)
            }
            listener.onClickFavorite(favoriteClick, item, position)
        }

    }

interface Listener {
    fun onClick(filmItem: FilmItem) {
    }

    fun onClickFavorite(checkBox: CheckBox, item: FilmItem, position: Int) {
    }
}
}















































/*fun delete(newFilm : KinopoiskFilmItem){
        var position = filmList.indexOf(newFilm)
        filmList.removeAt(position)
        notifyItemRemoved(position)
    }
    fun addFilm(newFilm: KinopoiskFilmItem){
        var position = filmList.indexOf(newFilm)
        filmList.add(position+1,newFilm)
        notifyDataSetChanged()
    }

    class FilmsViewHolder(item: View) : RecyclerView.ViewHolder(item) {
        private val binding = CardLayoutBinding.bind(item)
        fun bind(filmList: MutableList<KinopoiskFilmItem>, position: Int, listener: Listener) = with(binding){
            val item = filmList[position]
            cardTitle.text = item.nameRu
            cardSubtitle.text = item.year as String
            itemView.setOnClickListener{
                listener.onClick(item)
            }

            listener.onClickFavorite(favoriteClick, item, position)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FilmsViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.card_layout, parent, false)
        return FilmsViewHolder(view)
    }

    override fun onBindViewHolder(holder: FilmsViewHolder, position: Int) {
        Log.d("ON BIND VIEW HOLDER", "onBindViewHolder: $position")
        holder.bind(filmList,position,listener)
    }

    override fun getItemCount(): Int {
        return filmList.size
    }

    interface Listener{
        fun onClick(filmItem: KinopoiskFilmItem){
        }

        fun onClickFavorite(checkBox: CheckBox, item: KinopoiskFilmItem, position: Int) {
        }
    }
}*/