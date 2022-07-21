package com.example.sandbox.MainActivity_Fragments.presentation

import android.app.AlertDialog
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.sandbox.MainActivity_Fragments.presentation.screens.filmList.FilmListViewModel
import com.example.sandbox.MainActivity_Fragments.presentation.adapter.Adapter
import com.example.sandbox.MainActivity_Fragments.presentation.screens.favoriteFilms.FragmentFavoriteFilms
import com.example.sandbox.MainActivity_Fragments.presentation.screens.filmList.FragmentFilmList
import com.example.sandbox.R
import com.example.sandbox.databinding.ActivityMainBinding
import com.example.sandbox.databinding.AlertDialogExitBinding

class MainActivity : AppCompatActivity(), Adapter.Listener {
    lateinit var binding: ActivityMainBinding
    private lateinit var VM: FilmListViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        NavigationFun()
        supportFragmentManager
            .beginTransaction()
            .replace(R.id.frame_main, FragmentFilmList(), "film_list")
            .commit()
    }
    private fun NavigationFun(){
        binding.btnNav.setOnItemSelectedListener {

            when(it.itemId){
                R.id.button2_menu ->{
                    supportFragmentManager
                        .beginTransaction()
                        .replace(R.id.frame_main, FragmentFavoriteFilms())
                        .commit()
                }

                R.id.button1_menu ->{
                    supportFragmentManager
                        .beginTransaction()
                        .replace(R.id.frame_main, FragmentFilmList())
                        .commit()
                }
            }
            true
        }
    }
    override fun onBackPressed() {

        val myFragment: Fragment? = supportFragmentManager.findFragmentByTag("details_fragment")

        if (myFragment != null && myFragment.isVisible) {
            super.onBackPressed()
        } else {
            val dialogBinding = AlertDialogExitBinding.inflate(layoutInflater)
            val dialogAlert = AlertDialog.Builder(this)
            val dialog = dialogAlert.apply {
                setView(dialogBinding.root)
            }.create()
            dialogBinding.positiveBtn.setOnClickListener() {
                super.onBackPressed()
            }
            dialogBinding.negativeBtn.setOnClickListener() {
                Toast.makeText(this, "Спасибо, что вы с нами", Toast.LENGTH_LONG).show()
                dialog.dismiss()
            }
            dialog.show()
        }
    }
}

