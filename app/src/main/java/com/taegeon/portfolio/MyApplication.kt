package com.taegeon.portfolio

import android.app.Application
import com.taegeon.portfolio.net.DaumApiManager
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import org.koin.dsl.module

class MyApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidLogger()
            androidContext(this@MyApplication)
            modules(daumApiModule)
        }
    }
}

val daumApiModule = module {
    single { DaumApiManager() }
}