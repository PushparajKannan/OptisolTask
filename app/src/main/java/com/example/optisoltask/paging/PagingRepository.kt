package com.example.optisoltask.paging

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.optisoltask.network.WebServices
import com.example.optisoltask.view.model.Videos
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

class PagingRepository @Inject constructor(
    private val apiLists: WebServices
) : PagingSource<Int, Videos>() {
    override fun getRefreshKey(state: PagingState<Int, Videos>): Int? {
        return 1
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Videos> {

        val page = params.key ?: 1

        return try {
            val response = apiLists.apiVideosResponseList(page.toString())
            if (response.data.isNotEmpty()) {
                LoadResult.Page(
                    response.data, prevKey = if (page <= response.total_pages) null else page - 1,
                    nextKey = if (page == response.total_pages) null else page + 1
                )
            } else {
                return LoadResult.Error(throwable = Throwable())
            }

        } catch (exception: IOException) {
            return LoadResult.Error(exception)
        } catch (exception: HttpException) {
            return LoadResult.Error(exception)
        }
    }
}