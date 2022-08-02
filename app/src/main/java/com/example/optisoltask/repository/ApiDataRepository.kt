package com.example.optisoltask.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.example.optisoltask.network.WebServices
import com.example.optisoltask.paging.PagingRepository
import com.example.optisoltask.repository.ApiDataRepository.ApiError.Companion.EMPTY_API_ERROR
import com.example.optisoltask.view.model.Videos
import com.google.gson.Gson
import com.google.gson.annotations.SerializedName
import kotlinx.coroutines.flow.Flow
import retrofit2.HttpException
import javax.inject.Inject

class ApiDataRepository @Inject constructor(
    private val apiLists: WebServices
) {

    fun fetchVideoList(pagingConfig: PagingConfig = getDefaultPageConfig()): Flow<PagingData<Videos>> {
        return Pager(
            config = pagingConfig,
            pagingSourceFactory = { PagingRepository(apiLists) }
        ).flow
    }

    /**
     * let's define page size, page size is the only required param, rest is optional
     */
    private fun getDefaultPageConfig(): PagingConfig {
        return PagingConfig(pageSize = 2, enablePlaceholders = false)
    }


    /**
     * When 400 Error Response
     */

    data class ApiError(
        val code: Int,
        @SerializedName("response") var result: String? = "",
        @SerializedName("message") var message: String? = ""
    ) {
        companion object {
            val EMPTY_API_ERROR = ApiError(-1, "", "")
        }
    }


    fun Throwable.getApiError(): ApiError? {
        if (this is HttpException) {
            try {
                val errorJsonString = this.response()?.errorBody()?.string()

                val error = Gson().fromJson(errorJsonString, ApiError::class.java)
                return ApiError(400, error.result, error.message)
            } catch (exception: Exception) {
                // Ignore
                return EMPTY_API_ERROR
            }
        }
        return EMPTY_API_ERROR
    }

}