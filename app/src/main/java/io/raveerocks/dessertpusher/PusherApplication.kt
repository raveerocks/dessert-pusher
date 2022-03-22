package io.raveerocks.dessertpusher

import android.app.Application
import timber.log.Timber

class PusherApplication : Application() {
   override fun onCreate() {
       Timber.i("onCreate started")
       super.onCreate()
       Timber.plant(Timber.DebugTree())
       Timber.i("onCreate completed")
   }
}