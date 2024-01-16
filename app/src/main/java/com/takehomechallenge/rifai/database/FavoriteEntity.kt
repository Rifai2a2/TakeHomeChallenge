package com.takehomechallenge.rifai.database

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize

@Entity
@Parcelize
data class FavoriteEntity(
    @PrimaryKey
    @ColumnInfo(name = "username")
    val name: String,

    @ColumnInfo(name = "imagePath")
    val image: String,

    @ColumnInfo(name = "species")
    val species: String,

    @ColumnInfo(name = "gender")
    val gender: String,

    @ColumnInfo(name = "location")
    val location: String,

    @ColumnInfo(name = "origin")
    val origin: String
): Parcelable