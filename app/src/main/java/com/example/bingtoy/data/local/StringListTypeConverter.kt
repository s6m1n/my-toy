package com.example.bingtoy.data.local

import androidx.room.TypeConverter

class StringListTypeConverter {
    @TypeConverter
    fun fromList(value: List<String>): String = value.joinToString(",")

    @TypeConverter
    fun toList(value: String): List<String> = value.split(",")
}
