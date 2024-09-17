package com.example.mymobileapp.ui

import com.example.mymobileapp.LoginRequest
import com.example.mymobileapp.LoginResponse
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface AuthService {
    @POST("footscray/auth")
    fun login(@Body loginRequest: LoginRequest): Call<LoginResponse>

    @GET("dashboard/{keypass}")
    fun getDashboard(@Path("keypass") keypass: String): Call<DashboardResponse>
}