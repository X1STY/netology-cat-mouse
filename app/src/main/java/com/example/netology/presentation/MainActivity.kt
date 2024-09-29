package com.example.netology.presentation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.netology.domain.GameStatisticsRepository
import com.example.netology.ui.theme.NetologyTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val repository = GameStatisticsRepository(this)
        setContent {
            NetologyTheme {
                val navController = rememberNavController()
                Navigation(navController, repository)
            }
        }
    }
}


@Composable
fun Navigation(navController: NavHostController, repository: GameStatisticsRepository) {
    NavHost(navController, startDestination = "splash") {
        composable("splash") { SplashScreen(navController) }
        composable("start") { StartScreen(navController) }
        composable("game/{mouseCount}/{mouseSpeed}/{mouseSize}") { args ->
            val mouseCount = args.arguments?.getString("mouseCount")?.toInt() ?: 1
            val mouseSpeed = args.arguments?.getString("mouseSpeed")?.toFloat() ?: 1f
            val mouseSize = args.arguments?.getString("mouseSize")?.toFloat() ?: 50f
            GameScreen(mouseCount, mouseSpeed, mouseSize, repository, navController)
        }
        composable("statistics") { StatisticsScreen(repository) }
    }
}
