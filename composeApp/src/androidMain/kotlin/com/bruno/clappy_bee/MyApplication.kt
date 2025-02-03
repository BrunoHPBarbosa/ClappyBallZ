package com.bruno.clappy_bee

import android.app.Application
import com.bruno.clappy_bee.di.initializeKoin
import org.koin.android.ext.koin.androidContext

class MyApplication: Application()  {
    override fun onCreate() {
        super.onCreate()
        initializeKoin{
            androidContext(this@MyApplication)
        }
    }
}