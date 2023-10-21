package com.example.androidtaskbosta.di

import android.content.Context
import com.example.androidtaskbosta.common.Constants
import com.example.androidtaskbosta.data.remote.ApiPlaceholderService
import com.example.androidtaskbosta.data.repository.DataRepositoryImpl
import com.example.androidtaskbosta.domain.repository.DataRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object
AppModule {

    @Singleton
    @Provides
    fun provideContext(@ApplicationContext context: Context) = context


    @Singleton
    @Provides
    fun provideApiPlaceholderService(): ApiPlaceholderService {
        return Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl(Constants.BASE_URL)
            .build()
            .create(ApiPlaceholderService::class.java)
    }

    @Singleton
    @Provides
    fun provideDataRepository(
        apiPlaceholderService: ApiPlaceholderService
    ): DataRepository =
        DataRepositoryImpl(apiService = apiPlaceholderService)




}