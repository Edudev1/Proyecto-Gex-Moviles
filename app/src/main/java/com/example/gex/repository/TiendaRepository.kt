package com.example.gex.repository

import android.app.admin.TargetUser
import com.example.gex.dto.AddProductToCartDto
import com.example.gex.dto.CartItemDto
import com.example.gex.dto.CartResponseDto
import com.example.gex.dto.ProductDto
import com.example.gex.internet.RetrofitClient
import com.example.gex.model.LoginRequest
import com.example.gex.model.LoginResponse
import retrofit2.Response


class TiendaRepository {

    private val retrofitClient = RetrofitClient()
    private val apiService = retrofitClient.getApiService()

    suspend fun login(
        username: String,
        password: String
    ): Response<LoginResponse> {
        val loginRequest = LoginRequest(
            username = username,
            password = password
        )
        return apiService.login(loginRequest)
    }
    suspend fun getProducts(token: String): Response<List<ProductDto>> {
        return apiService.getProducts("Bearer $token")
    }
    suspend fun getCart(token: String): Response<CartResponseDto> {
        return apiService.getCart("Bearer $token")
    }

    suspend fun addProductToCart(
        token: String,
        productId: Long,
        units: Int
    ): Response<CartResponseDto> {
        val addProductToCartDto = AddProductToCartDto(
            productId = productId,
            units = units
        )
        return apiService.addProductToCart(
            "Bearer $token",
            addProductToCartDto
        )
    }

    suspend fun removeProductFromCart(
        token: String,
        productId: Long
    ): Response<CartResponseDto>{
        return apiService.removeProductFromCart(
            "Bearer $token",
            productId
        )
    }
}