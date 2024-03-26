package com.example.spotme.database

import androidx.room.TypeConverter
import java.util.Date

/**
 * Used to convert a date object into a
 * storable form and then back again.
 *
 */
class DateConverter() {
    /**
     * Converts a date object into a Long.
     * @param date a Date object to be converted.
     * @return a Long representing the date.
     */
    @TypeConverter
    fun dateToLong(date: Date?): Long? {
        return date?.time
    }

    /**
     * Converts a long into a Date object.
     * @param timestamp a long representing the date.
     * @return a Date object.
     */
    @TypeConverter
    fun longToDate(timestamp: Long?) : Date? {
        return timestamp?.let { Date(it) }
    }
}