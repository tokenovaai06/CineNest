package com.example.cinenest

import android.app.Application
import com.example.cinenest.data.AppContainer

class CineNestApplication : Application() {

    lateinit var container: AppContainer

    override fun onCreate() {
        super.onCreate()
        container = AppContainer(this)
    }
}
