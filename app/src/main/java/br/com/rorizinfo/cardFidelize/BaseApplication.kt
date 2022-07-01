package br.com.rorizinfo.cardFidelize

import android.app.Application
import br.com.rorizinfo.cardFidelize.di.modules
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.loadKoinModules
import org.koin.core.context.startKoin
import org.koin.core.logger.Level

class BaseApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(this@BaseApplication)
            androidLogger(Level.ERROR)
            loadKoinModules(modules)
        }
    }
}