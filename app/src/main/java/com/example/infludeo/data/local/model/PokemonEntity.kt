package com.example.infludeo.data.local.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import com.example.infludeo.data.local.StringListTypeConverter
import com.example.infludeo.domain.model.PokemonDetail
import com.example.infludeo.domain.model.PokemonType

@Entity(tableName = "pokemon")
@TypeConverters(StringListTypeConverter::class)
data class PokemonEntity(
    @PrimaryKey(autoGenerate = false) val id: Long = 0,
    @ColumnInfo(name = "name") val name: String,
    @ColumnInfo(name = "height") val height: Int,
    @ColumnInfo(name = "weight") val weight: Int,
    @ColumnInfo(name = "types") val types: List<String>,
    @ColumnInfo(name = "image_url") val imageUrl: String,
    @ColumnInfo(name = "image_path") val imagePath: String?,
)

fun PokemonEntity.toDomain(): PokemonDetail =
    PokemonDetail(
        id = id,
        name = name,
        height = height,
        weight = weight,
        types = types.map { PokemonType(it, "") },
        imageUrl = imageUrl,
        imagePath = imagePath,
    )

fun PokemonDetail.toEntity(): PokemonEntity {
    return PokemonEntity(
        id = id,
        name = name,
        height = height,
        weight = weight,
        types = types.map { it.name },
        imageUrl = imageUrl,
        imagePath = imagePath,
    )
}
