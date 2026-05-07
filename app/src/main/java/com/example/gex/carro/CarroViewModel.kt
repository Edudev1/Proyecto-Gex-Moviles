package com.example.gex.carro

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.gex.dto.CartItemDto
import com.example.gex.repository.TiendaRepository
import com.google.gson.stream.JsonToken
import kotlinx.coroutines.launch

class CarroViewModel : ViewModel() {

    private val repository = TiendaRepository()

    var productosCarro by mutableStateOf<List<CartItemDto>>(emptyList())
    var productosDistintos by mutableStateOf(0L)
    var unidadesTotales by mutableStateOf(0L)
    var precioTotal by mutableStateOf(0.0)

    var cargando by mutableStateOf(false)
    var error by mutableStateOf("")

    fun cargarCarro(token: String) {
        cargando = true
        error = ""

        viewModelScope.launch {
            try {
                val respuesta = repository.getCart(token)

                if (respuesta.isSuccessful){
                    val body = respuesta.body()

                    if (body != null) {
                        productosCarro = body.products
                        productosDistintos = body.distinctProducts
                        unidadesTotales = body.totalUnits
                        precioTotal = body.totalPrice
                    } else {
                        error = "Sin respuesta del server"
                    }
                } else {
                    error = "Error al cargar el carro"
                }
            } catch (e: Exception) {
                error = "No se ha podido cargar el carro"
            }

            cargando = false
        }
    }
    fun eliminarProducto(
        token: String,
        productId: Long
    ) {
        cargando = true
        error = ""

        viewModelScope.launch {
            try {
                val respuesta = repository.removeProductFromCart(
                    token = token,
                    productId = productId
                )
                if (respuesta.isSuccessful){
                    val body = respuesta.body()

                    if (body != null) {
                        productosCarro = body.products
                        productosDistintos = body.distinctProducts
                        unidadesTotales = body.totalUnits
                        precioTotal = body.totalPrice
                    } else {
                        error = "Sin respuesta del server"
                    }
                } else {
                    error = "Error al eliminar el producto"
                }
            } catch (e: Exception) {
                error = "No se ha podido eliminar el producto"
            }
            cargando = false
        }
    }
}






















