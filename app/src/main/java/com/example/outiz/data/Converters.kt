package com.example.outiz.data

import androidx.room.TypeConverter
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.Date

class Converters {
    private val formatter = DateTimeFormatter.ISO_LOCAL_DATE_TIME

    @TypeConverter
    fun fromLocalDateTime(value: LocalDateTime?): String? {
        return value?.format(formatter)
    }

    @TypeConverter
    fun toLocalDateTime(value: String?): LocalDateTime? {
        return value?.let { LocalDateTime.parse(it, formatter) }
    }

    @TypeConverter
    fun fromDate(date: Date?): Long? {
        return date?.time
    }

    @TypeConverter
    fun toDate(timestamp: Long?): Date? {
        return timestamp?.let { Date(it) }
    }

    @TypeConverter
    fun convertActionsList(actions: List<String>?): String? {
        return actions?.joinToString("|")
    }

    @TypeConverter
    fun parseActionsList(actionsString: String?): List<String>? {
        return actionsString?.split("|")
    }

    @TypeConverter
    fun convertPhotosPaths(paths: List<String>?): String? {
        return paths?.joinToString("|")
    }

    @TypeConverter
    fun parsePhotosPaths(pathsString: String?): List<String>? {
        return pathsString?.split("|")
    }
}