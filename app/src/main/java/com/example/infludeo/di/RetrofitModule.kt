package com.example.infludeo.di

import com.example.infludeo.data.remote.PokemonApiService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RetrofitModule {
    @Singleton
    @Provides
    fun providePokemonApiService(retrofit: Retrofit): PokemonApiService = retrofit.create(PokemonApiService::class.java)
}
