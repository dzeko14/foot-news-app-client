package my.dzeko.footapp.converter

import android.arch.persistence.room.TypeConverter
import java.util.*

class DateConverter {
    @TypeConverter
    fun convertToLong(date: Date): Long {
        return date.time
    }

    @TypeConverter
    fun convertToDate(dateTime: Long): Date{
        return Date(dateTime)
    }
}