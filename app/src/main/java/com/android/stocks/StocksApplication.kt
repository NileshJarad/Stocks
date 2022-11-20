package com.android.stocks

import android.app.Application
import com.android.stocks.data.di.datModule
import com.android.stocks.di.uiModule
import com.android.stocks.domain.di.domainModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class StocksApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        initDi()
    }

    private fun initDi() {
        startKoin {
            androidContext(applicationContext)
            modules(uiModule)
            modules(domainModule)
            modules(datModule)
        }
    }
}