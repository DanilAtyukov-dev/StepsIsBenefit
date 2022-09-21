package com.danilatyukov.linkedmoney.ui.pedometer

import android.content.Context
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.danilatyukov.linkedmoney.App
import com.danilatyukov.linkedmoney.appContext
import com.danilatyukov.linkedmoney.ui.pedometer.listener.StepListener
import com.danilatyukov.linkedmoney.ui.pedometer.utils.StepDetector

class PedometerViewModel : ViewModel(), SensorEventListener, StepListener{
    // TODO: Implement the ViewModel
    private var simpleStepDetector: StepDetector? = null
    private var sensorManager: SensorManager? = null
    private val textNumSteps = "Steps: "
    private var numSteps: Int = 0

    init {
        // Get an instance of the SensorManager
        sensorManager = App.it().appContext.getSystemService(Context.SENSOR_SERVICE) as SensorManager
        simpleStepDetector = StepDetector()
        simpleStepDetector!!.registerListener(this)
    }

    private val _counter = MutableLiveData<String>().apply {
        value = "number of steps"
    }
    val counter: LiveData<String> = _counter

    override fun onAccuracyChanged(p0: Sensor?, p1: Int) {
    }

    override fun onSensorChanged(event: SensorEvent?) {
        if (event!!.sensor.type == Sensor.TYPE_ACCELEROMETER) {
            simpleStepDetector!!.updateAccelerometer(event.timestamp, event.values[0], event.values[1], event.values[2])
        }
    }

    override fun step(timeNs: Long) {
        numSteps++
        _counter.value = textNumSteps.plus(numSteps)
    }

    fun play(){
        numSteps = 0
        sensorManager!!.registerListener(this, sensorManager!!.getDefaultSensor(Sensor.TYPE_ACCELEROMETER), SensorManager.SENSOR_DELAY_FASTEST)
    }

    fun stop(){
        sensorManager!!.unregisterListener(this)
    }
}