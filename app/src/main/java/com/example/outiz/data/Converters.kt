package com.example.outiz.data

import androidx.room.TypeConverter
import java.time.LocalDateTime
import java.time.ZoneOffset
import java.util.Date

class Converters {
    @TypeConverter
    fun fromTimestamp(value: Long?): LocalDateTime? {
        return value?.let { LocalDateTime.ofEpochSecond(it, 0, ZoneOffset.UTC) }
    }

    @TypeConverter
    fun dateToTimestamp(date: LocalDateTime?): Long? {
        return date?.toEpochSecond(ZoneOffset.UTC)
    }

    @TypeConverter
    fun fromDate(date: Date?): LocalDateTime? {
        return date?.toInstant()?.atZone(ZoneOffset.UTC)?.toLocalDateTime()
    }

    @TypeConverter
    fun toDate(localDateTime: LocalDateTime?): Date? {
        return localDateTime?.toInstant(ZoneOffset.UTC)?.let { Date.from(it) }
    }

    @TypeConverter
    fun fromStringList(value: String?): List<String>? {
        return value?.split(",")?.map { it.trim() }
    }

    @TypeConverter
    fun toStringList(list: List<String>?): String? {
        return list?.joinToString(",")
    }
}