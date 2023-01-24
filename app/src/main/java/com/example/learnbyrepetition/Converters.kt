package com.example.learnbyrepetition

import androidx.room.TypeConverter
import java.util.Date
import kotlin.time.Duration.Companion.milliseconds

class Converters {
    @TypeConverter
    fun fromDate(date: Date?): Long? {
        return date?.time
    }

    @TypeConverter
    fun toDate(long: Long?): Date? {
        return long?.let { Date(it) }
    }
}