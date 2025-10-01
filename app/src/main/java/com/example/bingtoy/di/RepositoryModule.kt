package com.example.bingtoy.di

import com.example.bingtoy.domain.repository.PokemonDefaultRepository
import com.example.bingtoy.domain.repository.PokemonRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {
    @Binds
    abstract fun bindPokemonRepository(repositoryImpl: PokemonDefaultRepository): PokemonRepository
}
