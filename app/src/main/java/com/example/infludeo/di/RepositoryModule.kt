package com.example.infludeo.di

import com.example.infludeo.data.repository.PokemonDefaultRepository
import com.example.infludeo.domain.repository.PokemonRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent

@Module
@InstallIn(ViewModelComponent::class)
abstract class RepositoryModule {
    @Binds
    abstract fun bindPokemonRepository(repositoryImpl: PokemonDefaultRepository): PokemonRepository
}
