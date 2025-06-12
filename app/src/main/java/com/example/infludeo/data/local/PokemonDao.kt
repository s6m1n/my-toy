package com.example.infludeo.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.infludeo.data.local.model.PokemonEntity
import com.example.infludeo.domain.model.DeleteResult
import com.example.infludeo.domain.model.InsertResult
import kotlinx.coroutines.flow.Flow

@Dao
interface PokemonDao {
    @Query("SELECT * FROM pokemon")
    fun getAll(): Flow<List<PokemonEntity>>

    @Query("SELECT * FROM pokemon WHERE id = :pokemonId")
    suspend fun getById(pokemonId: Long): PokemonEntity?

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertQuery(pokemonEntity: PokemonEntity): Long

    @Query("DELETE FROM pokemon WHERE id = :pokemonId")
    suspend fun deleteByIdQuery(pokemonId: Long): Int

    @Query("SELECT COUNT(*) FROM pokemon")
    suspend fun getCount(): Int

    suspend fun insertWithLimit(pokemon: PokemonEntity): InsertResult {
        if (getCount() >= 10) return InsertResult.LimitReached
        val newRowId = insertQuery(pokemon)
        return if (newRowId == -1L) {
            InsertResult.Duplicated
        } else {
            InsertResult.Success
        }
    }

    suspend fun deleteIfExists(pokemonId: Long): DeleteResult {
        val deletedCount = deleteByIdQuery(pokemonId)
        return if (deletedCount > 0) DeleteResult.Success else DeleteResult.Fail
    }
}
