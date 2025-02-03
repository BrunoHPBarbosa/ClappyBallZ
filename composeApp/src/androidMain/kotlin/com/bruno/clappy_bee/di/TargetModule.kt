package com.bruno.clappy_bee.di

import com.bruno.clappy_bee.domain.AudioPlayer
import org.koin.android.ext.koin.androidContext
import org.koin.core.module.Module
import org.koin.dsl.module

actual val targetModule =  module{
    single<AudioPlayer> { AudioPlayer(context = androidContext()) }
}