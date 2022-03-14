package com.example.finalapp_nativeprog

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import retrofit2.Retrofit
import retrofit2.awaitResponse
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.create

class YesNoViewModel: ViewModel() {

    // Get path
    private val PATH = "https://yesno.wtf/"
    val imageURL = mutableStateOf("")   // Store image url
    val answer = mutableStateOf("")     // Store answer

    // Get api
    private val api by lazy {
        Retrofit
            .Builder()
            .baseUrl(PATH)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create<MyImageApi>()
    }

    fun getNewImage() {
        viewModelScope.launch {
            val resp = api.getYesNo().awaitResponse()
            if(resp.isSuccessful){
                val map = resp.body()
                imageURL.value = map!!["image"].toString()
                answer.value = map!!["answer"].toString()
            }
        }
    }
}