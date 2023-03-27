package com.example.simplestore.application

import android.app.Application
import dagger.hilt.android.HiltAndroidApp

lateinit var application: SimpleStoreApplication
    private set

@HiltAndroidApp
class SimpleStoreApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        initApplication()
    }

    private fun initApplication() {
        application = this
    }

}