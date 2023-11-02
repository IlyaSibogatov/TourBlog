package com.example.tourblog.data.services

import com.example.tourblog.data.models.CommonResponse
import com.example.tourblog.data.models.blogs.BlogsCard
import com.example.tourblog.data.models.details.DetailsData
import com.example.tourblog.data.models.main.MainData
import retrofit2.http.GET
import retrofit2.http.Query
import retrofit2.http.Url

interface InformationService {
    @GET("main?id=117")
    suspend fun getMainPage(): CommonResponse<MainData>

    @GET
    suspend fun getBlogs(
        @Url query: String
    ): CommonResponse<List<BlogsCard>>

    @GET("blog-info?id=117")
    suspend fun getBlogDetails(
        @Query("blog_id") blogId: String
    ): CommonResponse<DetailsData>
}