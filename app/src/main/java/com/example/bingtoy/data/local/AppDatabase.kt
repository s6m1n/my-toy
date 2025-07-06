package com.example.bingtoy.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.bingtoy.data.local.model.PokemonEntity

@Database(
    entities = [PokemonEntity::class],
    version = 1,
)
abstract class AppDatabase : RoomDatabase() {
    abstract fun pokemonDao(): PokemonDao
}
