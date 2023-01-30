package com.example.learnbyrepetition.ui.shakeMe

import android.content.Context
import android.content.Intent
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import com.example.learnbyrepetition.R
import com.example.learnbyrepetition.database.DatabaseFlashcards
import com.example.learnbyrepetition.database.classes.Flashcard
import com.example.learnbyrepetition.databinding.FragmentHomeBinding
import com.example.learnbyrepetition.databinding.FragmentShakeMeBinding
import com.example.learnbyrepetition.newActivities.DetailsFlashcardActivity
import com.example.learnbyrepetition.ui.home.HomeViewModel
import kotlinx.coroutines.launch

class ShakeMeFragment: Fragment() {
    private var _binding: FragmentShakeMeBinding? = null
    private val binding get() = _binding!!

    private lateinit var sensorManager: SensorManager
    private val SHAKE_THRESHOLD = 2

    var flashcards: List<Flashcard>? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentShakeMeBinding.inflate(inflater, container, false)
        val root: View = binding.root

        lifecycleScope.launch() {
            val db = DatabaseFlashcards.getDatabase(layoutInflater.context)
            flashcards = db.flashcardDao().getAll()
        }

        sensorManager = activity?.getSystemService(Context.SENSOR_SERVICE) as SensorManager

        return root
    }

    private val sensorListener = object : SensorEventListener {
        override fun onSensorChanged(event: SensorEvent) {
            val x = event.values[0]
            val y = event.values[1]
            val z = event.values[2]

            val gX = x / SensorManager.GRAVITY_EARTH
            val gY = y / SensorManager.GRAVITY_EARTH
            val gZ = z / SensorManager.GRAVITY_EARTH

            val gForce = Math.sqrt((gX * gX + gY * gY + gZ * gZ).toDouble()).toFloat()

            if (gForce > SHAKE_THRESHOLD) {
                val intent = Intent(layoutInflater.context, DetailsFlashcardActivity::class.java)

                var bundle = Bundle()
                bundle.putLong(layoutInflater.context.getString(R.string.bundle_selected_flashcard_id), flashcards!!.random().id_flashcard)

                intent.putExtras(bundle)

                layoutInflater.context.startActivity(intent)
            }
        }

        override fun onAccuracyChanged(sensor: Sensor?, accuracy: Int) { }
    }

    override fun onResume() {
        super.onResume()
        sensorManager.registerListener(sensorListener,
            sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER),
            SensorManager.SENSOR_DELAY_NORMAL)
    }

    override fun onPause() {
        super.onPause()
        sensorManager.unregisterListener(sensorListener)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}