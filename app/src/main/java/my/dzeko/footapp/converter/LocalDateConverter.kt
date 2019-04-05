package my.dzeko.footapp.converter

import android.arch.persistence.room.TypeConverter
import org.joda.time.DateTime

class DateConverter {
    @TypeConverter
    fun convertToLong(date: DateTime): Long {
        return date.millis
    }

    @TypeConverter
    fun convertToDate(dateTime: Long): DateTime{
        return DateTime(dateTime)
    }
}