package com.example.netology.presentation

import androidx.compose.foundation.layout.*
import androidx.compose.material3.Button
import androidx.compose.material3.Slider
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController

@Composable
fun StartScreen(navController: NavController) {
    var mouseSize by remember { mutableFloatStateOf(50f) }
    var mouseSpeed by remember { mutableFloatStateOf(1f) }
    var mouseCount by remember { mutableFloatStateOf(1f) }

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
        modifier = Modifier.fillMaxSize()
    ) {
        Text(text = "Размер мышки: ${mouseSize.toInt()}")
        Slider(
            value = mouseSize,
            onValueChange = { mouseSize = it },
            valueRange = 20f..100f,
            modifier = Modifier.padding(16.dp)
        )

        Text(text = "Скорость мышки: ${mouseSpeed.toInt()}")
        Slider(
            value = mouseSpeed,
            onValueChange = { mouseSpeed = it },
            valueRange = 1f..10f,
            modifier = Modifier.padding(16.dp)
        )

        Text(text = "Количество мышек: ${mouseCount.toInt()}")
        Slider(
            value = mouseCount,
            onValueChange = { mouseCount = it },
            valueRange = 1f..5f,
            steps = 4,
            modifier = Modifier.padding(16.dp)
        )
        Button(onClick = { navController.navigate("game/${mouseCount.toInt()}/${mouseSpeed.toInt()}/${mouseSize.toInt()}") }) {
            Text(text = "Начать игру")
        }
        Spacer(modifier = Modifier.height(16.dp))
        Button(onClick = { navController.navigate("statistics") }) {
            Text(text = "Посмотреть статистику")
        }
    }
}
