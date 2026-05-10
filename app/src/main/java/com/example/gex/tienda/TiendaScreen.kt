package com.example.gex.tienda

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ExitToApp
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.gex.carro.CarroScreen
import com.example.gex.home.HomeScreen
import com.example.gex.productos.ProductosScreen

@Composable
fun TiendaScreen(
    token: String,
    usuario: String,
    onSalirClick: () -> Unit
) {
    var tabSeleccionado by remember { mutableIntStateOf(0) }

    Column(
        modifier = Modifier.fillMaxSize()
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(10.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                text = usuario
            )

            IconButton(
                onClick = onSalirClick
            ) {
                Icon(
                    imageVector = Icons.Default.ExitToApp,
                    contentDescription = "Salir"
                )
            }
        }

        TabRow(
            selectedTabIndex = tabSeleccionado
        ) {
            Tab(
                selected = tabSeleccionado == 0,
                onClick = {
                    tabSeleccionado = 0
                },
                text = {
                    Text(text = "Home")
                },
                icon = {
                    Icon(
                        imageVector = Icons.Default.Home,
                        contentDescription = "Home"
                    )
                }
            )

            Tab(
                selected = tabSeleccionado == 1,
                onClick = {
                    tabSeleccionado = 1
                },
                text = {
                    Text(text = "Productos")
                },
                icon = {
                    Icon(
                        imageVector = Icons.Default.Star,
                        contentDescription = "Productos"
                    )
                }
            )

            Tab(
                selected = tabSeleccionado == 2,
                onClick = {
                    tabSeleccionado = 2
                },
                text = {
                    Text(text = "Mi Carro")
                },
                icon = {
                    Icon(
                        imageVector = Icons.Default.ShoppingCart,
                        contentDescription = "Mi Carro"
                    )
                }
            )
        }

        Box(
            modifier = Modifier.weight(1f)
        ) {
            when (tabSeleccionado) {
                0 -> HomeScreen()
                1 -> ProductosScreen(token = token)
                2 -> CarroScreen(token = token)
            }
        }
    }
}