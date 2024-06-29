package com.beatrice.rickymorty

import android.app.Application
import com.beatrice.rickymorty.di.appModules
import org.koin.core.context.GlobalContext.startKoin

class RickyApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        startKoin()
    }

    fun startKoin() {
        startKoin {
            modules(appModules)
        }
    }
}
