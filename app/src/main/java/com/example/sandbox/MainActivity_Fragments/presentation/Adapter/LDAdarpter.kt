package com.example.sandbox.MainActivity_Fragments.presentation.Adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.ImageView
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.sandbox.R
import com.example.sandbox.databinding.CardLayoutBinding

class LDAdarpter(private val listener: LDAdarpter.Listener): PagingDataAdapter<FilmItem, LDAdarpter.FilmsPageViewHolder>(ARTICLE_DIFF_CALLBACK) {

    class FilmsPageViewHolder(
        private val binding: CardLayoutBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        private val poster: ImageView = itemView.findViewById(R.id.card_poster)

        fun bind(item: FilmItem, listener: Listener) {
            binding.apply {
                this.cardTitle.text = item.title
                this.cardSubtitle.text = item.subtitle

                Glide.with(this.cardView)
                    .load(item.poster)
                    .override(poster.resources.getDimensionPixelSize(R.dimen.poster_size))
                    .into(poster)
            }
            itemView.setOnClickListener{
                listener.onClick(item)
            }
            listener.onClickFavorite(binding.favoriteClick, item, position)
        }
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FilmsPageViewHolder =
        FilmsPageViewHolder(
            CardLayoutBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    override fun onBindViewHolder(holder: FilmsPageViewHolder, position: Int) {
        val tile = getItem(position)
        if(tile != null)
            holder.bind(tile, listener)
    }


    companion object {
        private val ARTICLE_DIFF_CALLBACK = object : DiffUtil.ItemCallback<FilmItem>() {
            override fun areItemsTheSame(oldItem: FilmItem, newItem: FilmItem): Boolean =
                oldItem.id == newItem.id

            override fun areContentsTheSame(oldItem: FilmItem, newItem: FilmItem): Boolean =
                oldItem == newItem
        }
    }
    interface Listener {
        fun onClick(filmItem: FilmItem) {
        }

        fun onClickFavorite(checkBox: CheckBox, item: FilmItem, position: Int) {
        }
    }
}
