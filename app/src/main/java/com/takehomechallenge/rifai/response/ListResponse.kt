package com.takehomechallenge.rifai.response

import android.os.Parcelable
import kotlinx.parcelize.Parcelize


data class ListResponse(
	val results: List<ResultsItem>
)

@Parcelize
data class ResultsItem(
	val name: String,
	val species: String,
	val gender: String,
	val origin: Origin,
	val location: Location,
	val image: String,
) : Parcelable

@Parcelize
data class Origin(
	val name: String,
	val url: String
) :Parcelable

@Parcelize
data class Location(
	val name: String,
	val url: String
) : Parcelable
