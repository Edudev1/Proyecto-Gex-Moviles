package com.example.gex

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import com.example.gex.login.LoginScreen
import com.example.gex.tienda.TiendaScreen

@Composable
fun AppNavegation() {
    var estaLogueado by remember { mutableStateOf(false) }
    var token by remember { mutableStateOf("") }
    var usuario by remember { mutableStateOf("") }

    if (estaLogueado) {
        TiendaScreen(
            token = token,
            usuario = usuario,
            onSalirClick = {
                estaLogueado = false
                token = ""
                usuario = ""
            }
        )
    } else {
        LoginScreen(
            onLoginCorrecto = {nuevoToken, nuevoUsuario ->
                token = nuevoToken
                usuario = nuevoUsuario
                estaLogueado = true
            }
        )
    }
}