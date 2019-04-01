package my.dzeko.footapp.converter

import android.arch.persistence.room.TypeConverter
import org.joda.time.Chronology
import org.joda.time.DateTime
import org.joda.time.DateTimeZone
import org.joda.time.chrono.ISOChronology
import java.util.*

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