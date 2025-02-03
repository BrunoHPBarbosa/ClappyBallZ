package com.bruno.clappy_bee

import androidx.compose.ui.window.ComposeUIViewController
import com.bruno.clappy_bee.di.initializeKoin

fun MainViewController() = ComposeUIViewController(
    configure = { initializeKoin() }
) { App() }