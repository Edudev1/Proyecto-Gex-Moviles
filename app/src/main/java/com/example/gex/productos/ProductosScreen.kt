package com.example.gex.productos

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.gex.dto.ProductDto
import androidx.compose.material3.Card

@Composable
fun ProductosScreen(
    token: String,
    productosViewModel: ProductosViewModel = viewModel()
) {
    val yaCargado = remember { mutableStateOf(false) }
    val productoParaAñadir = remember { mutableStateOf<ProductDto?>(null) }
    val unidades = remember { mutableStateOf(1) }

    if (!yaCargado.value) {
        productosViewModel.cargarProductos(token)
        yaCargado.value = true
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(10.dp)
    ) {
        Text(text = "Productos")

        if (productosViewModel.cargando) {
            Text(text = "Cargando productos, espere")
        }

        if (productosViewModel.error.isNotEmpty()) {
            Text(text = productosViewModel.error)
        }

        CategoriasView(
            categorias = productosViewModel.categorias,
            categoriaSeleccionada = productosViewModel.categoriaSeleccionada,
            onCategoriaClick = { categoria ->
                productosViewModel.filtrarPorCategoria(categoria)
            }
        )

        Text(
            text = "Página ${productosViewModel.paginaActual} de ${productosViewModel.getTotalPaginas()}"
        )

        LazyColumn(
            modifier = Modifier.weight(1f)
        ) {
            items(productosViewModel.getProductosPaginaActual()) { producto ->
                ProductoItem(
                    producto = producto,
                    onAddClick = { productoSeleccionado ->
                        productoParaAñadir.value = productoSeleccionado
                        unidades.value = 1
                    }
                )
            }
        }

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 8.dp),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Button(
                onClick = {
                    productosViewModel.anteriorPagina()
                },
                enabled = productosViewModel.paginaActual > 1
            ) {
                Text(text = "Anterior")
            }

            Button(
                onClick = {
                    productosViewModel.siguientePagina()
                },
                enabled = productosViewModel.paginaActual < productosViewModel.getTotalPaginas()
            ) {
                Text(text = "Siguiente")
            }
        }
    }
    if (productoParaAñadir.value != null){
        AlertDialog(
            onDismissRequest = {
                productoParaAñadir.value = null
            },
            title = {
                Text(text = "Añadir al carrito")
            },
            text = {
                Column {
                    Text(text = productoParaAñadir.value!!.productName)
                    Text(text = "Unidades: ${unidades.value}")

                    Row {
                        Button(
                            onClick = {
                                if (unidades.value > 1) {
                                    unidades.value--
                                }
                            }
                        ) {
                            Text(text = "-")
                        }
                        Button(
                            onClick = {
                                unidades.value++
                            }
                        ) {
                            Text(text = "+")
                        }
                    }
                }
            },
            confirmButton = {
                Button(
                    onClick = {
                        productosViewModel.añadirProductoAlCarro(
                            token = token,
                            productId = productoParaAñadir.value!!.productId.toLong(),
                            units = unidades.value
                        )
                        productoParaAñadir.value = null
                    }
                ) {
                    Text(text = "Añadir")
                }
            },
            dismissButton = {
                Button(
                    onClick = {
                        productoParaAñadir.value = null
                    }
                ) {
                    Text(text = "Cancelar")
                }
            }
        )
    }
}

@Composable
fun CategoriasView(
    categorias: List<String>,
    categoriaSeleccionada: String,
    onCategoriaClick: (String) -> Unit
) {
    LazyRow {
        items(categorias) { categoria ->
            Button(
                onClick = {
                    onCategoriaClick(categoria)
                },
                modifier = Modifier.padding(4.dp)
            ) {
                if (categoria == categoriaSeleccionada) {
                    Text(text = "$categoria X")
                } else {
                    Text(text = categoria)
                }
            }
        }
    }
}
@Composable
fun ProductoItem(
    producto: ProductDto,
    onAddClick: (ProductDto) -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp),
        elevation = CardDefaults.cardElevation(4.dp)
    ) {
        Text(text = producto.productName)
        Text(text = producto.productDescription)
        Text(text = "Desarrolladora: ${producto.brand.brandName}")
        Text(text = "Precio: ${producto.productPrice} €")

        if (producto.productDiscount > 0) {
            Text(text = "Descuento: ${producto.productDiscount}%")
        }
        if (producto.categories.isNotEmpty()) {
            Text(text = "Categorias: ${obtenerTextoCategorias(producto)}")
        } else {
            Text(text = "Sin categoria")
        }
        Button(
            onClick = {
                onAddClick(producto)
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(text = "Añadir")
        }
    }
}

fun obtenerTextoCategorias(
    producto: ProductDto
): String {
    var texto = ""

    for (i in producto.categories.indices) {
        texto += producto.categories[i].name

        if (i < producto.categories.size -1) {
            texto += ", "
        }
    }
    return texto
}






















