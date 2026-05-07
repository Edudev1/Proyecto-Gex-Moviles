package com.example.gex.dto

data class CartItemDto(
    val productId: Long,
    val productName: String,
    val unitPrice: Double,
    val discount: Int,
    val discountedPrice: Double,
    val units: Int,
    val totalPrice: Double
)