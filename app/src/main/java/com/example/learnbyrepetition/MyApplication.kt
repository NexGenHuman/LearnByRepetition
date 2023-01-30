package com.example.learnbyrepetition

import android.app.Application
import android.content.Context
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager

class MyApplication : Application() {
    var sensorManager: SensorManager? = null
    var proximitySensor: Sensor? = null
    private var proximityThreshold = 1f
    private var touchEnabled = true

    override fun onCreate() {
        super.onCreate()
        sensorManager = getSystemService(Context.SENSOR_SERVICE) as SensorManager
        proximitySensor = sensorManager?.getDefaultSensor(Sensor.TYPE_PROXIMITY)

        if (proximitySensor != null) {
            sensorManager!!.registerListener(
                proximitySensorListener,
                proximitySensor,
                SensorManager.SENSOR_DELAY_NORMAL
            )
        }
    }

    private val proximitySensorListener = object : SensorEventListener {

        override fun onSensorChanged(event: SensorEvent) {
            if (event.sensor == proximitySensor) {
                touchEnabled = event.values[0] >= proximityThreshold
            }
        }

        override fun onAccuracyChanged(sensor: Sensor?, accuracy: Int) {}
    }

    fun isTouchEnabled(): Boolean {
        return touchEnabled
    }
}