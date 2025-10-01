package com.example.bingtoy.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import java.util.concurrent.TimeUnit
import javax.inject.Named
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
internal object OkHttpModule {
    @Singleton
    @Provides
    fun provideLoggingInterceptor(): HttpLoggingInterceptor = HttpLoggingInterceptor().apply { level = HttpLoggingInterceptor.Level.BODY }

    @Singleton
    @Provides
    fun provideBaseOkHttpClient(loggingInterceptor: HttpLoggingInterceptor): OkHttpClient {
        return OkHttpClient.Builder()
            .addInterceptor(loggingInterceptor)
            .connectTimeout(30, TimeUnit.SECONDS)
            .readTimeout(30, TimeUnit.SECONDS)
            .writeTimeout(30, TimeUnit.SECONDS)
            .build()
    }

    @Singleton
    @Provides
    @Named("WebSocketClient")
    fun provideWebSocketOkHttpClient(base: OkHttpClient): OkHttpClient =
        base.newBuilder()
            .readTimeout(0, TimeUnit.MILLISECONDS) // 무한대
            .pingInterval(30, TimeUnit.SECONDS) // keep-alive
            .build()
}
