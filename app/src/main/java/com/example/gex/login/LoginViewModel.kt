package com.example.gex.login

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.example.gex.repository.TiendaRepository
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.ui.input.pointer.PointerEventPass
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import java.lang.Exception
import kotlin.math.log


class LoginViewModel : ViewModel() {

    private val repository = TiendaRepository()

    var cargando by mutableStateOf(false)
    var error by mutableStateOf("")
    var token by mutableStateOf("")
    var usuario by mutableStateOf("")

    fun login(
        username: String,
        password: String,
        onLoginCorrecto: (String, String) -> Unit
    ) {
        cargando = true
        error = ""

        viewModelScope.launch {
            try {
                val respuesta = repository.login(
                    username = username,
                    password = password
                )

                if (respuesta.isSuccessful) {
                    val body = respuesta.body()

                    if (body != null) {
                        token = body.accessToken
                        usuario = username
                        error = ""

                        onLoginCorrecto(token, usuario)
                    } else {
                        error = "Sin respuesta del server"
                    }
                } else {
                    error = "El usuario o contraseña son incorrectos"
                }
            } catch (e: Exception) {
                error = "No se puede conectar con el server"
            }
            cargando = false
        }
    }
}