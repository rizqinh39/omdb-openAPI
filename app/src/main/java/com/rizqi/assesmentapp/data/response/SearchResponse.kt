package com.rizqi.assesmentapp.data.response

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

data class SearchResponse(
        @SerializedName("Response")
        val response: String?,
        @SerializedName("Search")
        val search: List<Search?>?,
        @SerializedName("totalResults")
        val totalResults: String?,
        @SerializedName("Error")
        val Error: String?
) {
    @Parcelize
    data class Search(
            @SerializedName("Poster")
            val poster: String?,
            @SerializedName("Title")
            val title: String?,
            @SerializedName("Type")
            val type: String?,
            @SerializedName("Year")
            val year: String?,
            @SerializedName("imdbID")
            val imdbID: String?
    ) : Parcelable
}