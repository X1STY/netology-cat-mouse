package com.example.netology.presentation

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.netology.domain.GameStatisticsRepository
import com.example.netology.model.GameStatistics

@Composable
fun StatisticsScreen(repository: GameStatisticsRepository) {
    val statistics = remember { mutableStateOf<List<GameStatistics>>(emptyList()) }

    LaunchedEffect(Unit) {
        statistics.value = repository.getLastGames()
    }

    Box(modifier = Modifier.fillMaxSize().padding(16.dp)) {
        if (statistics.value.isEmpty()) {
            Text(text = "No game statistics available.")
        } else {
            LazyColumn {
                items(statistics.value) { stat ->
                    Box {
                        Text(text = "Нажатий: ${stat.totalClicks}\nПопаданий: ${stat.mouseClicks}\nПроцент: ${stat.hitPercentage}%\nВремя: ${stat.gameDuration / 1000f} c")
                    }
                    HorizontalDivider(thickness = 2.dp)
                }
            }
        }
    }
}

