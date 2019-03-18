package com.rizqi.assesmentapp.data.network


import com.rizqi.assesmentapp.data.response.MovieDetailResponse
import com.rizqi.assesmentapp.data.response.SearchResponse
import io.reactivex.Single
import retrofit2.http.*

interface RestService {

    @GET(ApiConstant.ALL)
    fun searchMovies(
            @Query("apikey") apiKey: String,
            @Query("s") keyword: String,
            @Query("page") page: Int
    ): Single<SearchResponse>


    @GET(ApiConstant.ALL)
    fun searchMoviesDetail(
            @Query("apikey") apiKey: String,
            @Query("i") idMovies: String,
            @Query("plot") page: String
    ): Single<MovieDetailResponse>


}
