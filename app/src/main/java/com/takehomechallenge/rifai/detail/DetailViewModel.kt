package com.takehomechallenge.rifai.detail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.takehomechallenge.rifai.database.FavoriteEntity
import com.takehomechallenge.rifai.database.FavoriteRepository
import kotlinx.coroutines.launch

class DetailViewModel (private val repository: FavoriteRepository) : ViewModel() {
    private val _favoriteStatus = MutableLiveData<Boolean>()
    val favoriteStatus: LiveData<Boolean> = _favoriteStatus


    fun addFavoriteUser(favoriteUser: FavoriteEntity) {
        viewModelScope.launch {
            repository.insertUser(favoriteUser)
            _favoriteStatus.postValue(true)
        }
    }

    fun deleteFavoriteUser(favoriteUser: FavoriteEntity) {
        viewModelScope.launch {
            repository.deleteUser(favoriteUser)
            _favoriteStatus.postValue(false)
        }
    }

    fun getFavoriteUserByUsername(username: String): LiveData<FavoriteEntity> {
        return repository.getFavoriteEntityByUsername(username)
    }



}