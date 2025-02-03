package com.bruno.clappy_bee.util

enum class Platform {
    Android,
    Desktop,
    iOS,
    Web

}

expect fun getPlatform(): Platform