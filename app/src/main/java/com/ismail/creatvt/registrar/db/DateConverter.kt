package com.ismail.creatvt.registrar.db

import androidx.room.TypeConverter
import java.util.Date

object DateConverter {

    @JvmStatic
    @TypeConverter
    fun toDate(timestamp: Long): Date {
        return Date(timestamp)
    }

    @JvmStatic
    @TypeConverter
    fun toTimestamp(date:Date): Long{
        return date.time
    }
}