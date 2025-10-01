package com.example.bingtoy.di

import com.example.bingtoy.data.datasource.EchoDataSource
import com.example.bingtoy.data.remote.datasource.echo.EchoDataSourceImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class DataSourceModule {
    @Binds
    abstract fun bindEchoDataSource(dataSourceImpl: EchoDataSourceImpl): EchoDataSource
}
