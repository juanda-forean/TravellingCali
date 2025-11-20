package com.example.travellingcali

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.travellingcali.data.repository.ActividadRepository
import com.example.travellingcali.ui.theme.ActivityEditorScreen
import com.example.travellingcali.ui.theme.TravellingCaliTheme

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            TravellingCaliTheme {
                TravellingCaliApp()
            }
        }
    }
}

data class BottomNavItem(
    val route: String,
    val label: String,
    val icon: androidx.compose.ui.graphics.vector.ImageVector
)

@Composable
fun TravellingCaliApp() {
    val navController = rememberNavController()
    val actividadRepo = remember { ActividadRepository() }
    NavHost(
        navController = navController,
        startDestination = "home"
    ) {
        composable("home") {
            HomeScreen(
                navController = navController,
                actividadRepo = actividadRepo
            )
        }
        composable("search") {
            SearchScreen(navController = navController)
        }
        composable("profile") {
            ProfileScreen(navController = navController)
        }
        composable("editor") {
            ActivityEditorScreen(
                navController = navController,
                actividadRepo = actividadRepo
            )
        }
    }
}
