package com.rnd.jyong.notificationsaver.base

import android.app.Application
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class BaseApplication : Application() {
    init {
        instance = this
    }

    override fun onCreate() {
        super.onCreate()
    }

    companion object {
        var instance: BaseApplication
            private set
        var LOG_TAG = "jworldlab"
    }
}