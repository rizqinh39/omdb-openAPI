package com.rizqi.assesmentapp.data.network


import ApiConstant
import com.rizqi.assesmentapp.data.response.SearchResponse
import io.reactivex.Single
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST

interface RestService {

    @FormUrlEncoded
    @POST(ApiConstant.SEARCH)
    fun searchMovies(
        @Field("apikey") apiKey: String,
        @Field("s") keyword: String
    ): Single<SearchResponse>


}
