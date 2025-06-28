package com.example.bingtoy.di

import com.example.bingtoy.data.repository.EchoRepositoryImpl
import com.example.bingtoy.data.repository.PokemonDefaultRepository
import com.example.bingtoy.domain.repository.EchoRepository
import com.example.bingtoy.domain.repository.PokemonRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent

@Module
@InstallIn(ViewModelComponent::class)
abstract class RepositoryModule {
    @Binds
    abstract fun bindPokemonRepository(repositoryImpl: PokemonDefaultRepository): PokemonRepository

    @Binds
    abstract fun bindEchoRepository(repositoryImpl: EchoRepositoryImpl): EchoRepository
}
