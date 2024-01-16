package com.takehomechallenge.rifai.api

import com.takehomechallenge.rifai.response.ListResponse
import retrofit2.Call
import retrofit2.http.GET


interface ApiService {
    @GET("character")
    fun getUsers(): Call<ListResponse>

}