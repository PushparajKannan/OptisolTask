package com.example.optisoltask.network


import com.example.optisoltask.view.model.VideoResponseListModel
import retrofit2.http.GET
import retrofit2.http.Query

interface WebServices {


    /**
     * Coroutines API Call
     * Use Suspend KEY Word
     * **/

    @GET("users")
    suspend fun apiVideosResponseList(@Query("page") pageNumber: String): VideoResponseListModel

}