package com.example.tourblog.data.repositories

import com.example.tourblog.data.models.CommonResponse
import com.example.tourblog.data.models.blogs.BlogsCard
import com.example.tourblog.data.models.details.DetailsData
import com.example.tourblog.data.models.main.MainData
import com.example.tourblog.data.services.InformationService
import javax.inject.Inject

class InformationRepositoryImpl @Inject constructor(
    private val infoService: InformationService
) : InformationRepository {
    override suspend fun getMain(): CommonResponse<MainData> = infoService.getMainPage()

    override suspend fun getBlogs(query: String): CommonResponse<List<BlogsCard>> = infoService.getBlogs(query)

    override suspend fun getBlogDetails(blogId: String): CommonResponse<DetailsData> =
        infoService.getBlogDetails(blogId)
}