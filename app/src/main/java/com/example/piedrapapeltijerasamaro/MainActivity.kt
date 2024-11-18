package com.example.piedrapapeltijerasamaro

import Eleccion
import Elegido
import Estadisticas
import Ganador
import Intermedio
import Login
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.compositionLocalOf
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.room.Room
import com.example.piedrapapeltijerasamaro.dal.UserDatabase
import com.example.piedrapapeltijerasamaro.dal.UserEntity
import com.example.piedrapapeltijerasamaro.ui.theme.PiedraPapelTijerasAmaroTheme
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

class MainActivity : ComponentActivity() {
    companion object {
        lateinit var basedatos : UserDatabase
    }

    lateinit var todas : List<UserEntity>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        basedatos = Room.databaseBuilder(
            this, UserDatabase::class.java,"users-db"
        ).fallbackToDestructiveMigration().build()
        runBlocking {
            launch {
                todas = basedatos.userDao().getAll()
            }
        }
        enableEdgeToEdge()
        setContent {
            val navController = rememberNavController()
            val coroutineScope = rememberCoroutineScope()
            NavHost(
                navController = navController,
                startDestination = "login",
                modifier = Modifier.fillMaxSize(),
            ) {
                composable("login") {
                    Login(navController, coroutineScope)
                }
                composable("eleccion") {
                    Eleccion(navController)
                }
                composable("elegido/{opcion}") { backStackEntry ->
                    Elegido(
                        navController,
                        backStackEntry.arguments?.getString("opcion") ?: "0",
                        coroutineScope
                    )
                }
                composable("ganador/{ganador}") { backStackEntry ->
                    Ganador(
                        navController,
                        backStackEntry.arguments?.getString("ganador") ?: "0"
                    )
                }
                composable("intermedio") {
                    Intermedio(
                        navController,
                        coroutineScope
                    )
                }
                composable("estadisticas") {
                    Estadisticas(
                        navController,
                        coroutineScope
                    )
                }
            }
        }
    }
}

