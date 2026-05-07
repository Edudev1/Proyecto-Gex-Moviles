package com.example.gex.internet

import com.example.gex.dto.AddProductToCartDto
import com.example.gex.dto.CartResponseDto
import com.example.gex.dto.ProductDto
import com.example.gex.model.LoginRequest
import com.example.gex.model.LoginResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST
import retrofit2.http.Path

interface ApiService {

    @POST("auth/login")
    suspend fun login(
        @Body loginRequest: LoginRequest
    ): Response<LoginResponse>

    @GET("products")
    suspend fun getProducts(
        @Header("Authorization") token: String
    ): Response<List<ProductDto>>

    @GET("cart")
    suspend fun getCart(
        @Header("Authorization") token: String
    ): Response<CartResponseDto>

    @POST("cart")
    suspend fun addProductToCart(
        @Header("Authorization") token: String,
        @Body addProductToCartDto: AddProductToCartDto
    ): Response<CartResponseDto>

    @DELETE("cart/{productId}")
    suspend fun removeProductFromCart(
        @Header("Authorization") token: String,
        @Path("productId") productId: Long
    ): Response<CartResponseDto>
}