package com.example.piedrapapeltijerasamaro

import Eleccion
import Elegido
import Ganador
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.piedrapapeltijerasamaro.ui.theme.PiedraPapelTijerasAmaroTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            val navController = rememberNavController()

            NavHost(
                navController = navController,
                startDestination = "eleccion",
                modifier = Modifier.fillMaxSize(),
            ) {
                composable("eleccion") {
                    Eleccion(navController)
                }
                composable("elegido/{opcion}") { backStackEntry ->
                    Elegido(
                        navController,
                        backStackEntry.arguments?.getString("opcion") ?: "0"
                    )
                }
                composable("ganador/{ganador}") { backStackEntry ->
                    Ganador(
                        navController,
                        backStackEntry.arguments?.getString("ganador") ?: "0"
                    )
                }
            }
        }
    }
}

