package com.example.gex.dto

data class CartResponseDto(
    val products: List<CartItemDto>,
    val distinctProducts: Long,
    val totalUnits: Long,
    val totalPrice: Double
)