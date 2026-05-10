package com.example.gex.carro

import android.R.attr.onClick
import android.util.JsonToken
import android.widget.Button
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.gex.dto.CartItemDto

@Composable
fun CarroScreen(
    token: String,
    carroViewModel: CarroViewModel = viewModel()
) {
    val yaCargado = remember { mutableStateOf(false) }
    val productoSeleccionado = remember { mutableStateOf<CartItemDto?>(null) }

    if (!yaCargado.value) {
        carroViewModel.cargarCarro(token)
        yaCargado.value = true
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(10.dp)
    ) {
        Text(text = "Mi carro")

        if (carroViewModel.cargando) {
            Text(text = "Cargando carrito...")
        }
        if (carroViewModel.error.isNotEmpty()) {
            Text(text = carroViewModel.error)
        }
        if (carroViewModel.productosCarro.isEmpty() && !carroViewModel.cargando) {
            Text(text = "El carro esta vacio")
        }

        LazyColumn(
            modifier = Modifier.weight(1f)
        ) {
            items(carroViewModel.productosCarro) { producto ->
                CarroItem(
                    producto = producto,
                    onClick = {
                        productoSeleccionado.value = producto
                    }
                )
            }
        }
        Text(text = "Productos distintos: ${carroViewModel.productosDistintos}")
        Text(text = "Unidades totales: ${carroViewModel.unidadesTotales}")
        Text(text = "Total: ${carroViewModel.precioTotal}")
    }

    if (productoSeleccionado.value != null) {
        AlertDialog(
            onDismissRequest = {
                productoSeleccionado.value = null
            },
            title = {Text(text = "Eliminar produto")
            },
            text = {
                Text(
                    text = "¿Queires eliminar ${productoSeleccionado.value!!.productName} del carro?"
                )
            },
            confirmButton = {
                Button(
                    onClick = {
                        carroViewModel.eliminarProducto(
                            token = token,
                            productId = productoSeleccionado.value!!.productId
                        )
                        productoSeleccionado.value = null
                    }
                ) {
                    Text(text = "Si")
                }
            },
            dismissButton = {
                Button(
                    onClick = {
                        productoSeleccionado.value = null
                    }
                ) {
                    Text(text = "No")
                }
            }
        )
    }
}

@Composable
fun CarroItem(producto: CartItemDto, onClick: () -> Unit) {
    Card(
        onClick = onClick,
        modifier = Modifier
            .fillMaxWidth()
            .padding(6.dp),
        elevation = CardDefaults.cardElevation(4.dp)
    ) {
        Card(
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(text = producto.productName)
            Text(text = "Unidades: ${producto.units}")
            Text(text = "Precio unidad: ${producto.unitPrice} €")

            if (producto.discount > 0) {
                Text(text = "Descuento: ${producto.totalPrice} €")
            }
        }
    }
}
















