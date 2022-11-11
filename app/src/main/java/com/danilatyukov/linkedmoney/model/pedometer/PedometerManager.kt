package com.danilatyukov.linkedmoney.model.pedometer

import android.content.Context
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import com.danilatyukov.linkedmoney.App
import com.danilatyukov.linkedmoney.appContext
import com.danilatyukov.linkedmoney.model.pedometer.listener.StepListener
import com.danilatyukov.linkedmoney.model.pedometer.utils.StepDetector

class PedometerManager: SensorEventListener, StepListener {

    var isActive = false

    // TODO: Implement the ViewModel
    private var simpleStepDetector: StepDetector? = null
    private var sensorManager: SensorManager? = null
    private var numSteps: Int = 0
    private val preferences = App.it().appComponent.appPreferences


    init {
        sensorManager = App.it().appContext.getSystemService(Context.SENSOR_SERVICE) as SensorManager
        simpleStepDetector = StepDetector()
        simpleStepDetector!!.registerListener(this)
    }

    override fun onAccuracyChanged(p0: Sensor?, p1: Int) {
    }

    override fun onSensorChanged(event: SensorEvent?) {
        if (event!!.sensor.type == Sensor.TYPE_ACCELEROMETER) {
            simpleStepDetector!!.updateAccelerometer(
                event.timestamp,
                event.values[0],
                event.values[1],
                event.values[2]
            )
        }
    }


    override fun step(timeNs: Long) {
            preferences.incCurrentSteps(1)
            numSteps++

        if (numSteps%100==0){
            App.it().appComponent.statisticInteractor.create(100)
        }
    }


    fun start(){
        preferences.clearSentSteps()
        preferences.clearCurrentSteps()
        preferences.clearCurrentDistance()

        numSteps = 0
        sensorManager!!.registerListener(
            this,
            sensorManager!!.getDefaultSensor(Sensor.TYPE_ACCELEROMETER),
            SensorManager.SENSOR_DELAY_FASTEST
        )
    }

    fun stop(){
        val stepsRemainder = preferences.currentSteps - preferences.sentSteps
        println("остаток ?? $stepsRemainder, ${preferences.currentAds} ${preferences.sentSteps}")

        App.it().appComponent.statisticInteractor.create(stepsRemainder)



        sensorManager!!.unregisterListener(this)

        preferences.clearCurrentSteps()

    }
}