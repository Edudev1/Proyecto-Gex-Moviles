package com.example.gex.dto

data class ProductDto(
    val productId: Int,
    val productEan: String,
    val productName: String,
    val productDescription: String,
    val productImage: String?,
    val productPrice: Double,
    val productDiscount: Int,
    val brand: BrandDto,
    val categories: List<CategoryDto>
)
