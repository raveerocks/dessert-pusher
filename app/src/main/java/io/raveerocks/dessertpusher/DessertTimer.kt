package io.raveerocks.dessertpusher

import android.os.Handler
import android.os.Looper
import androidx.lifecycle.*
import timber.log.Timber

class DessertTimer(lifecycle: Lifecycle) : LifecycleEventObserver{
    var secondsCount = 0
    private var handler = Handler(Looper.getMainLooper())
    private lateinit var runnable: Runnable

    init {
        lifecycle.addObserver(this)
    }

    override fun onStateChanged(source: LifecycleOwner, event: Lifecycle.Event) = when(event){
        Lifecycle.Event.ON_START -> startTimer()
        Lifecycle.Event.ON_STOP -> stopTimer()
        else -> {Timber.i("Event${event.name}encountered")}
    }


    private fun startTimer() {
        runnable = Runnable {
            secondsCount++
            Timber.d("Timer is at : $secondsCount")
            handler.postDelayed(runnable, 1000)
        }
        handler.postDelayed(runnable, 1000)
        Timber.i("startTimer called")
    }

    private fun stopTimer() {
        handler.removeCallbacks(runnable)
        Timber.i("stopTimer called")
    }
}