package com.bruno.clappy_bee.di

import com.bruno.clappy_bee.domain.AudioPlayer
import org.koin.dsl.module

actual val targetModule = module {
    single<AudioPlayer> { AudioPlayer() }
}