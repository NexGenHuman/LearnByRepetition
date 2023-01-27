package com.example.learnbyrepetition.database

import androidx.room.TypeConverter
import java.util.Date

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