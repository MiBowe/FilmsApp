package com.example.sandbox.MainActivity_Fragments.presentation.screens.detailsFilm

import android.app.*
import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.fragment.app.FragmentActivity
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import com.bumptech.glide.Glide
import com.example.sandbox.MainActivity_Fragments.data.api.PostRequest.AlarmReceiver
import com.example.sandbox.MainActivity_Fragments.presentation.Adapter.FilmItem
import com.example.sandbox.MainActivity_Fragments.presentation.MainActivity
import com.example.sandbox.MainActivity_Fragments.presentation.MainActivity.Companion.FILM_ID
import com.example.sandbox.R
import com.example.sandbox.databinding.FragmentDetailsBinding
import com.google.android.material.datepicker.MaterialDatePicker
import com.google.android.material.timepicker.MaterialTimePicker
import com.google.android.material.timepicker.TimeFormat
import okhttp3.internal.notify
import java.text.SimpleDateFormat
import java.util.*

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
        view_model.filmDetails.observe(viewLifecycleOwner, Observer<FilmItem?>{ item ->
            binding.titleDetail.text = item.subtitle
            binding.toolbar.title = item.title
            Glide.with(binding.posterTool)
                .load(item.posterToolbar)
                .centerCrop()
                .into(binding.posterTool)
            })
        setAlarm()
        }

    private fun setAlarm(){

        binding.setAlarmBtn.setOnClickListener(){

            val datePicker = MaterialDatePicker.Builder.datePicker()
                .setTitleText("Когда вам напомнить?")
                .build()

            datePicker.addOnPositiveButtonClickListener {
                val calendar = Calendar.getInstance()
                val materialTimePicker = MaterialTimePicker.Builder()
                    .setTimeFormat(TimeFormat.CLOCK_12H)
                    .setHour(0)
                    .setMinute(0)
                    .setTitleText("Во сколько вам напомнить?")
                    .build()

                materialTimePicker.addOnPositiveButtonClickListener {
                    calendar.set(Calendar.SECOND,0)
                    calendar.set(Calendar.MILLISECOND,0)
                    calendar.set(Calendar.MINUTE, materialTimePicker.minute)
                    calendar.set(Calendar.HOUR, materialTimePicker.hour)

                    sendAlarm(requireContext(), calendar, filmId)
                }
                calendar.timeInMillis = it
                materialTimePicker.show(parentFragmentManager,"Time_picker_____________________________________>")
                Log.d("Alarm suka blyat!", "Alarm was sending $calendar , $filmId ")
            }
            datePicker.show(parentFragmentManager,"date_picker__________________________________________>")
        }
    }



    private fun getFilm() {
        view_model.getFilmByID(filmId)
    }

    private fun sendAlarm(context: Context, date : Calendar, filmId: Int){

        val alarmManager = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager
        val alarmIntent = Intent(context, AlarmReceiver::class.java)

        alarmIntent.putExtra(FILM_ID,filmId)
        Log.d("PENDINGSUKABLYATRABOTAI!!!!", "${alarmIntent.putExtra(FILM_ID, filmId)}")

        val alarmPendingIntent : PendingIntent = PendingIntent.getBroadcast(context, filmId, alarmIntent, PendingIntent.FLAG_MUTABLE or PendingIntent.FLAG_UPDATE_CURRENT)
        Log.d("PendingIntent to Broadcast ---------------------------------->", "${filmId}")
        alarmManager.setExactAndAllowWhileIdle(AlarmManager.RTC_WAKEUP, date.timeInMillis, alarmPendingIntent)
        Log.d("SendAlarm!!", "Код выполнен!     $date, $filmId")
    }

    companion object{
        fun NewInstance(id: Int): Fragment{
            val arguments = Bundle()
            arguments.putInt("film", id)
            val frag = DetailsFragment()
            frag.arguments = arguments
            return frag
        }
    }
}