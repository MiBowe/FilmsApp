package com.example.sandbox.MainActivity_Fragments.presentation

import android.app.*
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.os.Build
import android.os.Bundle
import android.util.AttributeSet
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.example.sandbox.MainActivity_Fragments.data.api.PostRequest.AlarmReceiver.Companion.ID_CHANNEL
import com.example.sandbox.MainActivity_Fragments.presentation.screens.detailsFilm.DetailsFragment
import com.example.sandbox.MainActivity_Fragments.presentation.screens.favoriteFilms.FragmentFavoriteFilms
import com.example.sandbox.MainActivity_Fragments.presentation.screens.filmList.FragmentFilmList
import com.example.sandbox.R
import com.example.sandbox.databinding.ActivityMainBinding
import com.example.sandbox.databinding.AlertDialogExitBinding


class MainActivity : AppCompatActivity(){
    lateinit var binding: ActivityMainBinding
    private lateinit var pushBroadcastReceiver: BroadcastReceiver

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        createNotificationChannel()
        NavigationFun()
        supportFragmentManager
            .beginTransaction()
            .replace(R.id.frame_main, FragmentFilmList(), "film_list")
            .commit()
    }

    override fun onStart() {
        super.onStart()
        onNewIntent(intent)
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

    override fun onNewIntent(intent: Intent?) {
        super.onNewIntent(intent)
        val bundle = intent!!.extras
        Log.d("workBundle", "${bundle?.getString(FILM_ID)}")
        val idST: String? = bundle?.getString(FILM_ID)
        val idFilm = idST?.toInt()
        Log.d("workStr", "$idST")
        Log.d("workInt", "$idFilm")
        if (bundle != null) {
            if(bundle.containsKey("detailsFragment")){
                supportFragmentManager
                    .beginTransaction()
                    .replace(R.id.frame_main, DetailsFragment.NewInstance(id = idFilm!!), "details_fragment")
                    .commit()
            }
        }
    }

    private fun createNotificationChannel(){
        val name = "Watch later reminder"
        val descriptionText = "Receive notifications to watch movies later"
        val importance = NotificationManager.IMPORTANCE_HIGH
        val channel = NotificationChannel(ID_CHANNEL, name, importance).apply {
            description = descriptionText
        }
        val notificationManager: NotificationManager =
            getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        notificationManager.createNotificationChannel(channel)
        Log.d("NotificationChannel-------------------------------------------------------------------------------->"," Was registered! ")

    }

    companion object {
    const val FILM_ID = "filmId"
    }

}


