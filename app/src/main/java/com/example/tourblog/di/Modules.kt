package com.example.tourblog.di

import com.example.tourblog.data.repositories.InformationRepositoryImpl
import com.example.tourblog.data.services.InformationService
import com.example.tourblog.utils.Constants.BASE_URL
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object Modules {

    @Provides
    @Singleton
    fun providesOkHttpClient(
        loggingInterceptor: HttpLoggingInterceptor
    ): OkHttpClient =
        OkHttpClient.Builder()
            .addInterceptor(loggingInterceptor)
            .build()

    @Provides
    @Singleton
    fun providerGson(): Gson =
        GsonBuilder().create()

    @Provides
    @Singleton
    fun providerGsonConvertFactory(gson: Gson): GsonConverterFactory =
        GsonConverterFactory.create(gson)


    @Provides
    @Singleton
    fun provideInformationService(
        okHttpClient: OkHttpClient,
        gsonConverterFactory: GsonConverterFactory
    ): InformationService {
        return providerService(okHttpClient, gsonConverterFactory, InformationService::class.java)
    }

    private fun <T> providerService(
        okHttpClient: OkHttpClient,
        gsonConverterFactory: GsonConverterFactory,
        clazz: Class<T>,
    ): T =
        createRetrofit(okHttpClient, gsonConverterFactory).create(clazz)

    private fun createRetrofit(
        okHttpClient: OkHttpClient,
        gsonConverterFactory: GsonConverterFactory
    ): Retrofit =
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(okHttpClient)
            .addConverterFactory(gsonConverterFactory)
            .build()


    @Provides
    @Singleton
    fun providerLoggingInterceptor() : HttpLoggingInterceptor =
        HttpLoggingInterceptor().apply { level = HttpLoggingInterceptor.Level.BODY }
}