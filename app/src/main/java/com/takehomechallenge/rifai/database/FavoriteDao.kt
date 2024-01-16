package com.takehomechallenge.rifai.database

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface FavoriteDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(favoriteUser: FavoriteEntity)

    @Delete
    suspend fun delete(favoriteUser: FavoriteEntity)

    @Query("SELECT * FROM FavoriteEntity WHERE username = :username")
    fun getFavoriteEntityByUsername(username: String): LiveData<FavoriteEntity>

    @Query("SELECT * FROM FavoriteEntity")
    fun getAllFavoriteEntity(): LiveData<List<FavoriteEntity>>


}