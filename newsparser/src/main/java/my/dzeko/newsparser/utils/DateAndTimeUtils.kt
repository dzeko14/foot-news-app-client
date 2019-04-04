package my.dzeko.newsparser.utils

import org.joda.time.DateTime
import org.joda.time.DateTimeZone
import org.joda.time.chrono.ISOChronology
import java.lang.Exception
import java.time.LocalDateTime
import java.time.ZoneId
import java.util.*

internal object DateAndTimeUtils {

    fun getDateFromString(dateStr :String) : DateTime {
       // val dayAndMonth = dateStr.split(" ")
        val dateTime = DateTime.now(DateTimeZone.forID("Europe/Kiev"))
        return dateTime
//                .withDayOfMonth(dayAndMonth[0].toInt())
//                .withMonthOfYear(getMonthInt(dayAndMonth[1]))
    }

    private fun getMonthInt(s: String): Int {
        return when(s.capitalize()) {
            "Января" -> 1
            "Февраля" -> 2
            "Марта" -> 3
            "Апреля" -> 4
            "Мая" -> 5
            "Июня" -> 6
            "Июля" -> 7
            "Августа" -> 8
            "Сентября" -> 9
            "Октября" -> 10
            "Ноября" -> 11
            "Декабря" -> 12
            else -> throw Exception("Wrong month")
        }
    }

    fun getDateWithTime(timeStr: String, localDateTime: DateTime): DateTime {
        val hoursAndMinutes = timeStr.split(":")
        return localDateTime
                .withHourOfDay(hoursAndMinutes[0].toInt())
                .withMinuteOfHour(hoursAndMinutes[1].toInt())
    }

    fun convertLocalDateTimeToDate(localDateTime: LocalDateTime) :Date {
       return Date.from(localDateTime.atZone(ZoneId.systemDefault()).toInstant())
    }
}