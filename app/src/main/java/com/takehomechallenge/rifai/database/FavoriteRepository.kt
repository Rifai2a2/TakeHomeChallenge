package com.takehomechallenge.rifai.database

import androidx.lifecycle.LiveData

class FavoriteRepository private constructor(
    private val wishlistDao: FavoriteDao,

    ){

    suspend fun insertUser(favoriteUser: FavoriteEntity) {
        wishlistDao.insert(favoriteUser)
    }

    suspend fun deleteUser(favoriteUser: FavoriteEntity) {
        wishlistDao.delete(favoriteUser)
    }

    fun getFavoriteEntityByUsername(username: String): LiveData<FavoriteEntity> {
        return wishlistDao.getFavoriteEntityByUsername(username)
    }
    fun getAllFavoriteUsers(): LiveData<List<FavoriteEntity>> {
        return wishlistDao.getAllFavoriteEntity()
    }

    companion object {
        @Volatile
        private var instance: FavoriteRepository? = null
        fun getInstance(
            newsDao: FavoriteDao,
        ): FavoriteRepository =
            instance ?: synchronized(this) {
                instance ?: FavoriteRepository(newsDao)
            }.also { instance = it }
    }
}