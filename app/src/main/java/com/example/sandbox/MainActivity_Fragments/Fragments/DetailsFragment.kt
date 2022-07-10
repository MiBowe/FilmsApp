package com.example.sandbox.MainActivity_Fragments.Fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.sandbox.MainActivity_Fragments.Adapter.FilmItem
import com.example.sandbox.databinding.FragmentDetailsBinding



class DetailsFragment : Fragment() {

    lateinit var binding : FragmentDetailsBinding
    lateinit var film : FilmItem

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentDetailsBinding.inflate(inflater)
        film = arguments?.getSerializable("film") as FilmItem
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.posterTool.setImageResource(film.poster)
        binding.toolbar.title = film.title
        binding.titleDetail.text = getText(film.details)

    }

    companion object{

        fun NewInstance(item: FilmItem): Fragment{
            val arguments = Bundle()
            arguments.putSerializable("film", item)
            val frag = DetailsFragment()
            frag.arguments = arguments
            return frag
        }
    }
}