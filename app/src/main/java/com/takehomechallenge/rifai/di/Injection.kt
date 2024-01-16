package com.takehomechallenge.rifai.di

import android.content.Context
import com.takehomechallenge.rifai.database.FavoriteRepository
import com.takehomechallenge.rifai.database.FavoriteRoomDatabase

object Injection {
    fun provideRepository(context: Context): FavoriteRepository {
        val database = FavoriteRoomDatabase.getInstance(context)
        val dao = database.wishlistDao()
        return FavoriteRepository.getInstance(dao)
    }
}