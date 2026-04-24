package com.example.gex.repository

import android.app.admin.TargetUser
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
    suspend fun getProducts(): Response<List<ProductDto>> {
        return apiService.getProducts()
    }
}