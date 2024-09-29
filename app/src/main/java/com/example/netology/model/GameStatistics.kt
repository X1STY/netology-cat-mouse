package com.example.netology.model

import androidx.room.PrimaryKey

data class GameStatistics(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val totalClicks: Int,
    val mouseClicks: Int,
    val hitPercentage: Float,
    val gameDuration: Long
)
