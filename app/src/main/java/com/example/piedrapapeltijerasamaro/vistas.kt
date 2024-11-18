import android.widget.Button
import android.widget.Space
import android.widget.Toast
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.piedrapapeltijerasamaro.R
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.TextField
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.toLowerCase
import com.example.piedrapapeltijerasamaro.MainActivity.Companion.basedatos
import com.example.piedrapapeltijerasamaro.dal.UserEntity
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import java.util.Locale
import kotlin.random.Random

var ptsJugador = 0
var ptsMaquina = 0
var nickUsuario = ""

@Composable
fun Eleccion(navController: NavController) {
    Column(
        Modifier
            .fillMaxSize()
            .padding(top = 40.dp, start = 16.dp, end = 16.dp),
    ) {
        Row(Modifier.fillMaxWidth()) {
            Column(
                Modifier
                    .weight(1f)
                    .padding(5.dp),
                horizontalAlignment = Alignment.Start
            ) {
                Box(
                    modifier = Modifier
                        .border(BorderStroke(2.dp, Color.Blue), shape = RoundedCornerShape(24.dp))
                        .padding(10.dp)
                ) {
                    Text(
                        text = "Resultado: $ptsMaquina - $ptsJugador"
                    )
                }
            }
            Column(
                Modifier
                    .weight(1f)
                    .padding(5.dp),
                horizontalAlignment = Alignment.End
            ) {
                Box(
                    modifier = Modifier
                        .border(BorderStroke(2.dp, Color.Blue), shape = RoundedCornerShape(24.dp))
                        .padding(10.dp)
                ) {
                    Text(
                        text = "Jugando como $nickUsuario"
                    )
                }
            }
        }

        Column(
            Modifier
                .weight(1f)
                .fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Image(
                painter = painterResource(id = R.drawable.robot),
                contentDescription = "Imagen de la robot",
                Modifier.size(150.dp,150.dp),
            )
            Text(
                modifier = Modifier.padding(top = 40.dp),
                fontSize = 20.sp,
                text = "La máquina está eligiendo...",
            )
        }
        HorizontalDivider(
            color = Color.Blue,
            thickness = 2.dp
        )
        Column(
            Modifier
                .weight(1f)
                .padding(20.dp)
                .fillMaxSize(),
        ) {
            Text(
                modifier = Modifier.fillMaxWidth(),
                fontSize = 25.sp,
                textAlign = TextAlign.Center,
                text = "Haz tu elección",
            )
            Column(
                Modifier
                    .fillMaxSize()
                    .padding(bottom = 50.dp),
                verticalArrangement = Arrangement.Bottom
            ) {
                Row() {
                    Button(
                        onClick = {
                            navController.navigate("elegido/1")
                        },
                        Modifier
                            .weight(1f)
                            .padding(5.dp),
                    ) {
                        Image(
                            painter = painterResource(id = R.drawable.piedra),
                            contentDescription = "Imagen de la piedra",
                            Modifier.size(50.dp,50.dp)
                        )
                    }
                    Button(
                        onClick = {
                            navController.navigate("elegido/2")
                        },
                        Modifier
                            .weight(1f)
                            .padding(5.dp),
                    ) {
                        Image(
                            painter = painterResource(id = R.drawable.papel),
                            contentDescription = "Imagen del papel",
                            Modifier.size(50.dp,50.dp)
                        )
                    }
                    Button(
                        onClick = {
                            navController.navigate("elegido/3")
                        },
                        Modifier
                            .weight(1f)
                            .padding(5.dp),
                    ) {
                        Image(
                            painter = painterResource(id = R.drawable.tijeras),
                            contentDescription = "Imagen de las tijeras",
                            Modifier.size(50.dp,50.dp)
                        )
                    }
                }
                Spacer(Modifier.height(20.dp))
                Button(
                    onClick = {
                        navController.navigate("intermedio")
                    },
                    Modifier.fillMaxWidth(),
                ) {
                    Text("Volver al menú")
                }
            }
        }
    }
}

@Composable
fun Elegido(navController: NavController, opcion: String, coroutine: CoroutineScope) {
    val context = LocalContext.current
    var maquina by remember { mutableStateOf(Random.nextInt(1, 4)) }
    var opcionInt = opcion.toInt()

    var maquinaEleccion = R.drawable.piedra
    var jugadorEleccion = R.drawable.piedra

    if (maquina == 2) {
        maquinaEleccion = R.drawable.papel
    } else if (maquina == 3) {
        maquinaEleccion = R.drawable.tijeras
    }

    if (opcionInt == 2) {
        jugadorEleccion = R.drawable.papel
    } else if (opcionInt == 3) {
        jugadorEleccion = R.drawable.tijeras
    }

    // Variables auxiliares para evitar que sume más de uno
    var pMaquina = ptsMaquina
    var pJugador = ptsJugador

    // Variable para controlar la visualización del Toast
    var showToast by remember { mutableStateOf(false) }

    if ((maquina == 1 && opcionInt == 3) || (maquina == 2 && opcionInt == 1) || (maquina == 3 && opcionInt == 2)) {
        if (!showToast) {
            Toast.makeText(context, "Oh vaya... Has perdido!", Toast.LENGTH_SHORT).show()
            showToast = true
        }
        pMaquina += 1
    } else if ((maquina == 3 && opcionInt == 1) || (maquina == 1 && opcionInt == 2) || (maquina == 2 && opcionInt == 3)) {
        if (!showToast) {
            Toast.makeText(context, "Enhorabuena! Has ganado", Toast.LENGTH_SHORT).show()
            // Añadimos una lucha ganada
            LaunchedEffect(Unit) {
                val user = basedatos.userDao().get(nickUsuario)
                user.lg += 1

                basedatos.userDao().update(user)
            }
            showToast = true
        }
        pJugador += 1
    } else {
        if (!showToast) {
            Toast.makeText(context, "Empate", Toast.LENGTH_SHORT).show()
            showToast = true
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = "Resultados",
            fontSize = 24.sp,
            color = Color.Blue,
            modifier = Modifier.padding(bottom = 16.dp)
        )

        // Elección de la máquina
        Column(
            modifier = Modifier
                .weight(1f)
                .fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Image(
                painter = painterResource(id = maquinaEleccion),
                contentDescription = "Eleccion de la máquina",
                modifier = Modifier.size(100.dp)
            )
            Text(text = "Máquina", fontSize = 18.sp, color = Color.Gray)
        }

        HorizontalDivider(
            color = Color.Blue,
            thickness = 2.dp,
            modifier = Modifier.padding(vertical = 16.dp)
        )

        // Elección del jugador
        Column(
            modifier = Modifier
                .weight(1f)
                .fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Image(
                painter = painterResource(id = jugadorEleccion),
                contentDescription = "Eleccion del jugador",
                modifier = Modifier.size(100.dp)
            )
            Text(text = "Tú", fontSize = 18.sp, color = Color.Gray)
        }

        Button(
            onClick = {
                ptsJugador = pJugador
                ptsMaquina = pMaquina

                if (ptsJugador == 3 || ptsMaquina == 3) {
                    if (ptsJugador > ptsMaquina) {
                        coroutine.launch {
                            // Sumamos una partida ganada y jugada
                            val user = basedatos.userDao().get(nickUsuario)

                            user.pg += 1
                            user.pj += 1
                            basedatos.userDao().update(user)
                        }
                        navController.navigate("ganador/0") {
                            popUpTo(navController.graph.startDestinationId) { inclusive = true }
                            launchSingleTop = true
                        }

                    } else {
                        coroutine.launch {
                            // Sumamos una partida jugada
                            val user = basedatos.userDao().get(nickUsuario)

                            user.pj += 1
                            basedatos.userDao().update(user)
                        }
                        navController.navigate("ganador/1") {
                            popUpTo(navController.graph.startDestinationId) { inclusive = true }
                            launchSingleTop = true
                        }
                    }
                } else {
                    navController.popBackStack()
                }
            },
            modifier = Modifier
                .padding(top = 24.dp)
                .fillMaxWidth()
        ) {
            Text(
                text = "Volver a jugar...",
                fontSize = 18.sp
            )
        }
    }
}

// Composable que muestra al ganador
@Composable
fun Ganador(navController: NavController, ganador: String) {
    var ganadorInt = ganador.toInt()
    var image = if (ganadorInt == 0) R.drawable.win else R.drawable.lose

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Image(
            painter = painterResource(id = image),
            contentDescription = "Resultado",
            modifier = Modifier.size(150.dp)
        )
        Button(
            onClick = {
                // Reiniciamos las puntuaciones
                ptsJugador = 0
                ptsMaquina = 0
                navController.navigate("eleccion") {
                    popUpTo(navController.graph.startDestinationId) { inclusive = true }
                    launchSingleTop = true
                }
            },
            modifier = Modifier.padding(top = 16.dp)
        ) {
            Text(text = "Volver a jugar")
        }
    }
}

// Composable que hace el login
@Composable
fun Login(navController: NavController, coroutine: CoroutineScope) {
    val context = LocalContext.current
    var username by remember { mutableStateOf("") }
    Column (
        Modifier.padding(10.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Text(
            text = "Nombre de usuario"
        )
        Spacer(Modifier.height(10.dp))
        TextField(
            value = username,
            onValueChange = { username = it.lowercase() },
            label = { Text("Nombre de usuario") },
            placeholder = { Text("Escribe tu nombre de usuario") },
            singleLine = true,
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        )
        Spacer(Modifier.height(10.dp))
        Button(
            onClick = {
                if (username != "") {
                    coroutine.launch {
                        // Comprobamos si ya existe ese nombre de usuario
                        val exists = basedatos.userDao().existsByNick(username)

                        // En caso de que no exista lo añadimos
                        if (!exists) {
                            val user = UserEntity(nick = username)
                            basedatos.userDao().insert(user)
                            nickUsuario = user.nick
                            Toast.makeText(context, "Creando a $username...", Toast.LENGTH_SHORT).show()
                        } else {
                            nickUsuario = username
                            Toast.makeText(context, "Iniciando como $username...", Toast.LENGTH_SHORT).show()
                        }
                    }

                    navController.navigate("intermedio")
                } else {
                    Toast.makeText(context, "Rellena los campos", Toast.LENGTH_SHORT).show()
                }
            }
        ) {
            Text(text = "Guardar")
        }
    }
}

// Composable intermediaria que muestra un menú de acciones al usuario
@Composable
fun Intermedio(navController: NavController, coroutine: CoroutineScope) {
    Column(
        Modifier.fillMaxSize().padding(horizontal = 20.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "¡Hola, $nickUsuario! ¿Qué quieres hacer?",
            fontSize = 18.sp
        )
        Spacer(Modifier.height(20.dp))
        Button(
            onClick = {
                navController.navigate("eleccion")
            },
            Modifier.fillMaxWidth()
        ) {
            Text("Jugar")
        }
        Spacer(Modifier.height(20.dp))
        Button(
            onClick = {
                navController.navigate("estadisticas")
            },
            Modifier.fillMaxWidth()
        ) {
            Text("Ver estadisticas")
        }
        Spacer(Modifier.height(20.dp))
        Button(
            onClick = {
                navController.navigate("login") {
                    // Reinciamos todos los datos
                    nickUsuario = ""
                    ptsJugador = 0
                    ptsMaquina = 0
                    popUpTo(navController.graph.startDestinationId) { inclusive = true }
                    launchSingleTop = true
                }
            },
            Modifier.fillMaxWidth()
        ) {
            Text("Cerrar sesión")
        }
        Spacer(Modifier.height(20.dp))
        DeleteUserButton(navController, coroutine)
    }
}

@Composable
fun Estadisticas(navController: NavController, coroutine: CoroutineScope) {
    var userM by remember { mutableStateOf(UserEntity()) }
    var allUsers by remember { mutableStateOf<List<UserEntity>>(emptyList()) }

    // Obtener estadísticas del usuario actual
    LaunchedEffect(Unit) {
        userM = basedatos.userDao().get(nickUsuario)

        // Obtener todas las estadísticas de los usuarios y ordenarlos
        allUsers = basedatos.userDao().getAll().sortedWith(
            compareByDescending<UserEntity> { it.pg } // Ordenar por pg de mayor a menor
                .thenBy { it.pj } // En caso de empate, ordenar por pj de menor a mayor
                .thenBy { it.lg } // En caso de empate, ordena por lg de menor a mayor
            // Por último ordena por nombre
        )
    }

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(20.dp)
    ) {
        Text(
            text = "Tus estadísticas",
            Modifier.padding(top = 10.dp)
        )
        Spacer(Modifier.height(10.dp))

        // Encabezado de la primera tabla
        Row(
            modifier = Modifier
                .background(Color.Gray)
                .padding(8.dp)
                .fillMaxWidth()
        ) {
            Text(
                text = "Partidas Jugadas",
                modifier = Modifier.weight(1f),
                color = Color.White
            )
            Text(
                text = "Luchas Ganadas",
                modifier = Modifier.weight(1f),
                color = Color.White
            )
            Text(
                text = "Partidas Ganadas",
                modifier = Modifier.weight(1f),
                color = Color.White
            )
        }

        // Filas de datos de la primera tabla
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp)
                .border(1.dp, Color.LightGray)
        ) {
            Text(
                text = userM.pj.toString(),
                modifier = Modifier.weight(1f)
            )
            Text(
                text = userM.lg.toString(),
                modifier = Modifier.weight(1f)
            )
            Text(
                text = userM.pg.toString(),
                modifier = Modifier.weight(1f)
            )
        }

        // Espacio entre las tablas
        Spacer(modifier = Modifier.height(20.dp))

        Text(
            text = "Estadísticas generales",
        )

        Spacer(Modifier.height(10.dp))

        // Encabezado de la segunda tabla (estadísticas de todos los usuarios)
        Row(
            modifier = Modifier
                .background(Color.Gray)
                .padding(8.dp)
                .fillMaxWidth()
        ) {
            Text(
                text = "Usuario",
                modifier = Modifier.weight(1f),
                color = Color.White
            )
            Text(
                text = "Partidas Jugadas",
                modifier = Modifier.weight(1f),
                color = Color.White
            )
            Text(
                text = "Luchas Ganadas",
                modifier = Modifier.weight(1f),
                color = Color.White
            )
            Text(
                text = "Partidas Ganadas",
                modifier = Modifier.weight(1f),
                color = Color.White
            )
        }

        // Filas de la segunda tabla (estadísticas de todos los usuarios)
        allUsers.forEach { user ->
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(6.dp)
                    .border(1.dp, Color.LightGray)
            ) {
                Text(
                    text = user.nick, // Asumiendo que `nick` es el nombre del usuario
                    modifier = Modifier.weight(1f)
                )
                Text(
                    text = user.pj.toString(),
                    modifier = Modifier.weight(1f)
                )
                Text(
                    text = user.lg.toString(),
                    modifier = Modifier.weight(1f)
                )
                Text(
                    text = user.pg.toString(),
                    modifier = Modifier.weight(1f)
                )
            }
        }
    }
}



// Composable que elimina al usuario
@Composable
fun DeleteUserButton(navController: NavController, coroutine: CoroutineScope) {
    var showDialog by remember { mutableStateOf(false) }
    val context = LocalContext.current

    Button(
        onClick = { showDialog = true },
        modifier = Modifier.fillMaxWidth()
    ) {
        Text("Eliminar este usuario")
    }

    // Mostramos un dialog para corroborar que desea eliminar al usuario
    if (showDialog) {
        AlertDialog(
            onDismissRequest = { showDialog = false },
            title = { Text("Confirmación") },
            text = { Text("¿Estás seguro de que deseas eliminar a $nickUsuario?") },
            confirmButton = {
                Button(
                    onClick = {
                        // Eliminamos al usuario de la bd y volvemos a la pantalla de login
                        coroutine.launch {
                            val user = basedatos.userDao().get(nickUsuario)
                            basedatos.userDao().delete(user)
                            Toast.makeText(context, "$nickUsuario eliminado", Toast.LENGTH_SHORT).show()
                            // Reinciamos todos los datos
                            nickUsuario = ""
                            ptsJugador = 0
                            ptsMaquina = 0
                        }
                        showDialog = false

                        navController.navigate("login") {
                            popUpTo(navController.graph.startDestinationId) { inclusive = true }
                            launchSingleTop = true
                        }
                    }
                ) {
                    Text("Sí")
                }
            },
            dismissButton = {
                Button(
                    onClick = { showDialog = false }
                ) {
                    Text("No")
                }
            }
        )
    }
}
