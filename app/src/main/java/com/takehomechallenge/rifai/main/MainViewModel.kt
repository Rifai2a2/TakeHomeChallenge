package com.takehomechallenge.rifai.main

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.takehomechallenge.rifai.api.ApiConfig
import com.takehomechallenge.rifai.api.ApiService
import com.takehomechallenge.rifai.response.ListResponse
import com.takehomechallenge.rifai.response.ResultsItem
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainViewModel : ViewModel() {
    private val _listReview = MutableLiveData<List<ResultsItem?>>()
    val listReview: LiveData<List<ResultsItem?>> = _listReview

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    private val apiService = ApiConfig.retrofit.create(ApiService::class.java)

    fun getUserData() {
        _isLoading.value = true

        apiService.getUsers().enqueue(object : Callback<ListResponse> {
            override fun onResponse(call: Call<ListResponse>, response: Response<ListResponse>) {
                _isLoading.value = false

                if (response.isSuccessful) {
                    _listReview.value = response.body()?.results
                } else {
                    Log.e(TAG, "onFailure: ${response.message()}")
                }
            }

            override fun onFailure(call: Call<ListResponse>, t: Throwable) {
                _isLoading.value = false
                Log.e(TAG, "onFailure: ${t.message.toString()}")
            }
        })
    }
    companion object{
        private const val TAG = "DetailViewModel"

    }
}