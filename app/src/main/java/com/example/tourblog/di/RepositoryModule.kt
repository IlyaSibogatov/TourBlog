package com.example.tourblog.di

import com.example.tourblog.data.repositories.InformationRepository
import com.example.tourblog.data.repositories.InformationRepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {
    @Binds
    @Singleton
    abstract fun provideInformationRepository(infoRepositoryImpl: InformationRepositoryImpl): InformationRepository
}