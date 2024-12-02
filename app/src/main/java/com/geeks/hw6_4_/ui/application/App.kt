package com.geeks.hw6_4_.ui.application

import android.app.Application
import com.geeks.hw6_4_.data.dataModule
import com.geeks.hw6_4_.ui.uiModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class App : Application() {

    override fun onCreate() {
        super.onCreate()
        startKoin{
            androidContext(this@App)
            modules(dataModule)
            modules(uiModule)
        }
    }
}