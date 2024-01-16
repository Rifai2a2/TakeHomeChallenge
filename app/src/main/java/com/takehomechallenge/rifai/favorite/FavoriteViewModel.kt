package com.takehomechallenge.rifai.favorite

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.takehomechallenge.rifai.database.FavoriteEntity
import com.takehomechallenge.rifai.database.FavoriteRepository

class FavoriteViewModel(private val repository: FavoriteRepository) : ViewModel() {

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    fun getAllFavoriteUsers(): LiveData<List<FavoriteEntity>> {
        return repository.getAllFavoriteUsers()
    }
}