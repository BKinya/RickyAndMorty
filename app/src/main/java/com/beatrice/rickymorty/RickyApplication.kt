package com.beatrice.rickymorty

import android.app.Application
import com.beatrice.rickymorty.di.appModules
import logcat.AndroidLogcatLogger
import logcat.LogPriority
import org.koin.core.context.GlobalContext.startKoin

class RickyApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        startKoin()

        // Log all priorities in debug builds, no-op in release builds.
        AndroidLogcatLogger.installOnDebuggableApp(this, minPriority = LogPriority.DEBUG)
    }

    private fun startKoin() {
        startKoin {
            modules(appModules)
        }
    }
}
