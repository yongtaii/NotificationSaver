package com.rnd.jyong.notificationsaver.base

import android.app.Application
import android.content.Context
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class App : Application() {

    override fun onCreate() {
        super.onCreate()
        App.appContext = applicationContext
    }


    companion object {
        lateinit  var appContext: Context
    }

}