package com.example.gex.login

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel

@Composable
fun LoginScreen(
    onLoginCorrecto: (String, String) -> Unit,
    loginViewModel: LoginViewModel = viewModel()
) {
    var username by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(20.dp)
    ) {
        Text(text = "Mi tienda")

        OutlinedTextField(
            value = username,
            onValueChange = { username = it},
            label = { Text(text ="Usuario")},
            modifier = Modifier.fillMaxWidth()
        )

        OutlinedTextField(
            value = password,
            onValueChange = { password = it},
            label = {Text("Contraseña")},
            visualTransformation = PasswordVisualTransformation(),
            modifier = Modifier.fillMaxWidth()
        )
        Button(
            onClick = {
                if (username.isNotEmpty() && password.isNotEmpty()) {
                    loginViewModel.login(
                        username = username,
                        password = password,
                        onLoginCorrecto = onLoginCorrecto
                    )
                }
            },
            modifier = Modifier.fillMaxWidth(),
            enabled = !loginViewModel.cargando
        ) {
            Text(text = "Entrar")
        }

        if (loginViewModel.cargando) {
            CircularProgressIndicator()
        }

        if (loginViewModel.error.isNotEmpty()) {
            Text(text = loginViewModel.error)
        }
    }
}