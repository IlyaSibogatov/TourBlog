package com.example.tourblog.data.repositories

import com.example.tourblog.data.models.CommonResponse
import com.example.tourblog.data.models.blogs.BlogsCard
import com.example.tourblog.data.models.details.DetailsData
import com.example.tourblog.data.models.main.MainData

interface InformationRepository {
    suspend fun getMain(): CommonResponse<MainData>

    suspend fun getBlogs(query: String): CommonResponse<List<BlogsCard>>

    suspend fun getBlogDetails(blogId: String): CommonResponse<DetailsData>
}