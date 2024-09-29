package com.example.netology.model

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.setValue

data class Mouse(
    var positionX: Float,
    var positionY: Float,
    val speed: Float,
    val size: Float,
    var direction: Float
) {
    var positionXState by mutableFloatStateOf(positionX)
    var positionYState by mutableFloatStateOf(positionY)
}
