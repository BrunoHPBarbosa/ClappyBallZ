package com.bruno.clappy_bee

import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.window.ComposeViewport
import com.bruno.clappy_bee.di.initializeKoin
import kotlinx.browser.document

@OptIn(ExperimentalComposeUiApi::class)
fun main() {
    initializeKoin()
    ComposeViewport(document.body!!) {
        App()
    }
}