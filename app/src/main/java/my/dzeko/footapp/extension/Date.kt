package my.dzeko.footapp.extension

import android.content.res.Resources
import my.dzeko.footapp.R
import org.joda.time.DateTime
import org.joda.time.chrono.ISOChronology
import org.joda.time.format.DateTimeFormatterBuilder

private const val DAY_IN_MILLS = 86_400_000
private const val HOUR_IN_MILLS = 3_600_000
private const val MINUTE_IN_MILLS = 60_000

fun DateTime.formattedTime(resource: Resources): String {
    val currentTime = DateTime.now()
    val diff = currentTime.millis - millis

    return when {
        diff <= HOUR_IN_MILLS -> {
            val hDiff = (diff / MINUTE_IN_MILLS).toInt()
            resource.getQuantityString(R.plurals.minutes,
                hDiff,
                hDiff)
        }

        diff <= DAY_IN_MILLS -> {
            val mDiff = (diff / HOUR_IN_MILLS).toInt()
            resource.getQuantityString(R.plurals.hours,
                mDiff,
                mDiff)
        }

        else -> {
            val formatter = DateTimeFormatterBuilder()
                .appendDayOfMonth(2)
                .appendLiteral('-')
                .appendMonthOfYear(2)
                .appendLiteral('-')
                .appendYear(4,4)
                .appendLiteral(' ')
                .appendHourOfDay(2)
                .appendLiteral(':')
                .appendMinuteOfDay(2)
                .toFormatter()
            return toString(formatter)
        }
    }

}