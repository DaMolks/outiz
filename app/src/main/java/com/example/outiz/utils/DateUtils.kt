package com.example.outiz.utils

import java.text.SimpleDateFormat
import java.util.*

object DateUtils {
    private val dateFormatter = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
    private val timeFormatter = SimpleDateFormat("HH:mm", Locale.getDefault())
    private val fullFormatter = SimpleDateFormat("dd/MM/yyyy HH:mm", Locale.getDefault())
    private val isoFormatter = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", Locale.US).apply {
        timeZone = TimeZone.getTimeZone("UTC")
    }

    fun formatDate(date: Date?): String = date?.let { dateFormatter.format(it) } ?: ""

    fun formatTime(date: Date?): String = date?.let { timeFormatter.format(it) } ?: ""

    fun formatFull(date: Date?): String = date?.let { fullFormatter.format(it) } ?: ""

    fun parseDate(dateStr: String): Date? = try {
        dateFormatter.parse(dateStr)
    } catch (e: Exception) {
        null
    }

    fun parseTime(timeStr: String): Date? = try {
        timeFormatter.parse(timeStr)
    } catch (e: Exception) {
        null
    }

    fun toISOString(date: Date): String = isoFormatter.format(date)

    fun fromISOString(isoStr: String): Date? = try {
        isoFormatter.parse(isoStr)
    } catch (e: Exception) {
        null
    }

    fun utcToLocal(utcDate: Date): Date {
        val localTimeZone = TimeZone.getDefault()
        return Date(utcDate.time + localTimeZone.getOffset(utcDate.time))
    }

    fun localToUtc(localDate: Date): Date {
        val localTimeZone = TimeZone.getDefault()
        return Date(localDate.time - localTimeZone.getOffset(localDate.time))
    }

    fun startOfWeek(): Date {
        val calendar = Calendar.getInstance()
        calendar.set(Calendar.DAY_OF_WEEK, calendar.firstDayOfWeek)
        return startOfDay(calendar.time)
    }

    fun startOfMonth(): Date {
        val calendar = Calendar.getInstance()
        calendar.set(Calendar.DAY_OF_MONTH, 1)
        return startOfDay(calendar.time)
    }

    private fun startOfDay(date: Date): Date {
        val calendar = Calendar.getInstance()
        calendar.time = date
        calendar.set(Calendar.HOUR_OF_DAY, 0)
        calendar.set(Calendar.MINUTE, 0)
        calendar.set(Calendar.SECOND, 0)
        calendar.set(Calendar.MILLISECOND, 0)
        return calendar.time
    }
}