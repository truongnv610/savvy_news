package com.savvy.data.base.service
import com.savvy.data.base.model.news.NewsPagination
import io.reactivex.rxjava3.core.Single
import retrofit2.http.*

interface NewsService {

    @GET("top-headlines")
    fun getListNews(
        @Query("apiKey") apiKey: String,
        @Query("country") country: String,
        @Query("page") page: Int,
        @Query("pageSize") pageSize: Int,
        @Query("q") searchKeyword: String
    ): Single<NewsPagination>
}