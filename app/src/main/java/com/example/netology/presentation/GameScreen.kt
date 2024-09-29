package com.example.netology.presentation

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.offset
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.netology.R
import com.example.netology.model.GameStatistics
import com.example.netology.domain.GameStatisticsRepository
import com.example.netology.model.Mouse
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlin.random.Random
import kotlin.math.cos
import kotlin.math.sin

@Composable
fun GameScreen(mouseCount: Int, mouseSpeed: Float, mouseSize: Float, repository: GameStatisticsRepository, navController: NavController) {
    val configuration = LocalConfiguration.current
    val screenWidth = configuration.screenWidthDp.dp
    val screenHeight = configuration.screenHeightDp.dp
    val mice = remember { mutableStateListOf<Mouse>() }
    var totalClicks by remember { mutableIntStateOf(0) }
    var mouseClicks by remember { mutableIntStateOf(0) }
    var hitPercentage by remember { mutableFloatStateOf(0f) }
    var gameDuration by remember { mutableLongStateOf(0L) }
    var isGameActive by remember { mutableStateOf(true) }
    var isPaused by remember { mutableStateOf(false) }
    val coroutineScope = rememberCoroutineScope()

    BackHandler { }

    LaunchedEffect(mouseCount) {
        mice.clear()
        repeat(mouseCount) {
            mice.add(
                Mouse(
                    Random.nextFloat() * 1000,
                    Random.nextFloat() * 1000,
                    mouseSpeed,
                    mouseSize,
                    Random.nextFloat() * (2 * Math.PI).toFloat()
                )
            )
        }
    }

    LaunchedEffect(Unit) {
        val startTime = System.currentTimeMillis()
        while (isGameActive && !isPaused) {
            gameDuration = System.currentTimeMillis() - startTime
            mice.forEach { mouse ->
                mouse.positionXState += cos(mouse.direction) * mouse.speed
                mouse.positionYState += sin(mouse.direction) * mouse.speed

                if (Random.nextInt(100) < 5) {
                    mouse.direction = Random.nextFloat() * (2 * Math.PI).toFloat()
                }
                if (mouse.positionXState > screenWidth.value) mouse.positionXState = 0f
                if (mouse.positionXState < 0) mouse.positionXState = screenWidth.value
                if (mouse.positionYState > screenHeight.value) mouse.positionYState = 0f
                if (mouse.positionYState < 0) mouse.positionYState = screenHeight.value
            }
            delay(16)
        }
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .pointerInput(Unit) {
                detectTapGestures {
                    run {
                        if (isGameActive && !isPaused) {
                            totalClicks++
                            hitPercentage = (mouseClicks.toFloat() / totalClicks) * 100
                        }
                    }
                }
            }
    ) {
        mice.forEach { mouse ->
            Image(
                painter = painterResource(id = R.drawable.mouse),
                contentDescription = "Mouse",
                modifier = Modifier
                    .size(mouse.size.dp)
                    .offset(mouse.positionXState.dp, mouse.positionYState.dp)
                    .pointerInput(Unit) {
                        detectTapGestures {
                            run {
                                if (isGameActive && !isPaused) {
                                    mouseClicks++
                                    totalClicks++
                                    hitPercentage = (mouseClicks.toFloat() / totalClicks) * 100
                                }
                            }
                        }
                    }
            )
        }
        Text(text = "Нажатий: $totalClicks\nПопаданий: $mouseClicks\nПроцент: ${hitPercentage.toInt()}%")

        Image(painter = painterResource(id = R.drawable.pause),
            contentDescription = "Pause",
            modifier = Modifier
                .size(50.dp)
                .clickable { isPaused = !isPaused }
                .align(Alignment.TopEnd))

        if (isPaused) {
            PauseDialog(
                onContinue = { isPaused = false },
                onEndGame = {
                    isGameActive = false
                    val statistics = GameStatistics(
                        totalClicks = totalClicks,
                        mouseClicks = mouseClicks,
                        hitPercentage = hitPercentage,
                        gameDuration = gameDuration
                    )
                    coroutineScope.launch {
                        repository.insert(statistics)
                    }
                    navController.navigate("start")
                }
            )
        }
    }
}

@Composable
fun PauseDialog(onContinue: () -> Unit, onEndGame: () -> Unit) {
    AlertDialog(
        onDismissRequest = { onContinue() },
        title = { Text(text = "Пауза") },
        text = { Text(text = "Вы хотите продолжить игру или завершить её?") },
        confirmButton = {
            Button(onClick = onContinue) {
                Text("Продолжить")
            }
        },
        dismissButton = {
            Button(onClick = onEndGame) {
                Text("Завершить игру")
            }
        }
    )
}
