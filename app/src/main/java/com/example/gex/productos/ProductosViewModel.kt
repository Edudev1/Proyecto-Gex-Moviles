package com.example.gex.productos

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.gex.dto.ProductDto
import com.example.gex.repository.TiendaRepository
import kotlinx.coroutines.launch

class ProductosViewModel : ViewModel() {

    private val repository = TiendaRepository()

    var productos by mutableStateOf<List<ProductDto>>(emptyList())
    var productosFiltrados by mutableStateOf<List<ProductDto>>(emptyList())
    var categorias by mutableStateOf<List<String>>(emptyList())

    var productoSeleccionado by mutableStateOf<ProductDto?>(null)

    var cargando by mutableStateOf(false)
    var error by mutableStateOf("")

    var categoriaSeleccionada by mutableStateOf("Todas")

    var paginaActual by mutableStateOf(1)
    val productosPorPagina = 5

    var mensajeCarrito by mutableStateOf("")
    var errorCarrito by mutableStateOf("")
    var cargandoCarrito by mutableStateOf(false)

    fun cargarProductos(token: String) {
        cargando = true
        error = ""

        viewModelScope.launch {
            try {
                val respuesta = repository.getProducts(token)

                if (respuesta.isSuccessful) {
                    val body = respuesta.body()

                    if (body != null) {
                        productos = body

                        categorias = obtenerCategorias(body)

                        filtrarPorCategoria("Todas")
                    } else {
                        error = "Respuesta vacía del servidor"
                    }
                } else {
                    error = "Error al cargar productos"
                }

            } catch (e: Exception) {
                error = "No se ha podido conectar con el servidor"
            }

            cargando = false
        }
    }

    private fun obtenerCategorias(listaProductos: List<ProductDto>): List<String> {
        val listaCategorias = mutableListOf<String>()

        listaCategorias.add("Todas")

        for (producto in listaProductos) {
            for (categoria in producto.categories) {
                if (!listaCategorias.contains(categoria.name)) {
                    listaCategorias.add(categoria.name)
                }
            }
        }

        return listaCategorias
    }

    fun filtrarPorCategoria(categoria: String) {
        categoriaSeleccionada = categoria
        paginaActual = 1

        if (categoria == "Todas") {
            productosFiltrados = productos
        } else {
            val listaFiltrada = mutableListOf<ProductDto>()

            for (producto in productos) {
                var pertenece = false

                for (cat in producto.categories) {
                    if (cat.name == categoria) {
                        pertenece = true
                    }
                }

                if (pertenece) {
                    listaFiltrada.add(producto)
                }
            }

            productosFiltrados = listaFiltrada
        }
    }

    fun getProductosPaginaActual(): List<ProductDto> {
        val inicio = (paginaActual - 1) * productosPorPagina
        var fin = inicio + productosPorPagina

        if (fin > productosFiltrados.size) {
            fin = productosFiltrados.size
        }

        if (inicio >= productosFiltrados.size) {
            return emptyList()
        }

        return productosFiltrados.subList(inicio, fin)
    }

    fun siguientePagina() {
        val totalPaginas = getTotalPaginas()

        if (paginaActual < totalPaginas) {
            paginaActual++
        }
    }

    fun anteriorPagina() {
        if (paginaActual > 1) {
            paginaActual--
        }
    }

    fun getTotalPaginas(): Int {
        if (productosFiltrados.isEmpty()) {
            return 1
        }

        var total = productosFiltrados.size / productosPorPagina

        if (productosFiltrados.size % productosPorPagina != 0) {
            total++
        }

        return total
    }

    fun añadirProductoAlCarro(
        token: String,
        productId: Long,
        units: Int
    ) {
        cargandoCarrito = true
        mensajeCarrito = ""
        errorCarrito = ""

        viewModelScope.launch {
            try {
                val respuesta = repository.addProductToCart(
                    token = token,
                    productId = productId,
                    units = units
                )

                if (respuesta.isSuccessful) {
                    mensajeCarrito = "Producto añadido al carrito"
                } else {
                    errorCarrito = "Error al añadir al carro"
                }
            } catch (e: Exception) {
                errorCarrito = "No se ha podido añadir al carrito"
            }
            cargandoCarrito = false
        }
    }
}














