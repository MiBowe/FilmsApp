package com.example.sandbox.MainActivity_Fragments.presentation.screens.detailsFilm

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import com.bumptech.glide.Glide
import com.example.sandbox.MainActivity_Fragments.presentation.Adapter.FilmItem
import com.example.sandbox.MainActivity_Fragments.presentation.Adapter.KinopoiskFilmItem
import com.example.sandbox.databinding.FragmentDetailsBinding
import java.util.*
import kotlin.math.log


class DetailsFragment : Fragment() {

    lateinit var binding : FragmentDetailsBinding
    var filmId : Int = 0
    private val view_model: DetailsFilmViewModel by viewModels()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        filmId = arguments?.getInt("film")!!
        binding = FragmentDetailsBinding.inflate(inflater)
        Log.d("BUNDLE___________________________________________________________>", "$filmId")
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        getFilm()
        view_model.filmDetails.observe(viewLifecycleOwner, Observer<FilmItem?>{
            binding.titleDetail.text = it.subtitle
            binding.toolbar.title = it.title
            Glide.with(binding.posterTool)
                .load(it.posterToolbar)
                .centerCrop()
                .into(binding.posterTool)
        })
    }

    private fun getFilm() {
        view_model.getFilmByID(filmId)
    }

    companion object{

        fun NewInstance(item: FilmItem): Fragment{
            val arguments = Bundle()
            arguments.putInt("film", item.id)
            val frag = DetailsFragment()
            frag.arguments = arguments
            return frag
        }
    }
}