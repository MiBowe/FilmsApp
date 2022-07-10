package com.example.sandbox.MainActivity_Fragments.Adapter

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import androidx.recyclerview.widget.RecyclerView
import com.example.sandbox.R
import com.example.sandbox.databinding.CardLayoutBinding

class Adapter(private val filmList: MutableList<FilmItem>, private val listener: Listener) : RecyclerView.Adapter<Adapter.FilmsViewHolder>() {

    fun delete(newFilm : FilmItem){
        var position = filmList.indexOf(newFilm)
        filmList.removeAt(position)
        notifyItemRemoved(position)
    }
    fun addFilm(newFilm: FilmItem){
        var position = filmList.indexOf(newFilm)
        filmList.add(position+1,newFilm)
        notifyDataSetChanged()
    }

    class FilmsViewHolder(item: View) : RecyclerView.ViewHolder(item) {
        private val binding = CardLayoutBinding.bind(item)
        fun bind(filmList: MutableList<FilmItem>, position: Int, listener: Listener) = with(binding){
            val item = filmList[position]
            cardPoster.setImageResource(item.poster)
            cardTitle.text = item.title
            cardSubtitle.text = item.subtitle
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
        fun onClick(filmItem: FilmItem){
        }

        fun onClickFavorite(checkBox: CheckBox, item: FilmItem, position: Int) {
        }
    }
}