package com.rizqi.assesmentapp.ui.view

import com.rizqi.assesmentapp.data.response.MovieDetailResponse

interface SearchDetailView {
    fun onSuccessMovieDetail(searchResponse: MovieDetailResponse)
    fun onErrorMovieDetail(t: Throwable)
}