package com.example.finalapp_nativeprog

import retrofit2.Call
import retrofit2.http.GET
import java.util.*

interface MyImageApi {
    // Get method api
    @GET("api")
    fun getYesNo(): Call<Map<String, Objects>>
}