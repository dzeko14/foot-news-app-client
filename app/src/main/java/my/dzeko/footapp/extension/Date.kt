package my.dzeko.footapp.extension

import android.content.res.Resources
import my.dzeko.footapp.R
import java.text.SimpleDateFormat
import java.util.*

private const val DAY_IN_MILLS = 86_400_000
private const val HOUR_IN_MILLS = 3_600_000

fun Date.formattedTime(resource: Resources): String {
    val diff = Date().time - this.time
    val calendar = Calendar.getInstance()
    val currentCalendar = Calendar.getInstance()
    calendar.time = this
    currentCalendar.time = Date()
    val minutes = calendar.get(Calendar.MINUTE)
    val hours = calendar.get(Calendar.HOUR_OF_DAY)
    return when {
        diff <= HOUR_IN_MILLS -> {
            val hDiff = currentCalendar.get(Calendar.MINUTE) - minutes
            resource.getQuantityString(R.plurals.minutes,
                hDiff,
                hDiff)
        }

        diff <= DAY_IN_MILLS -> {
            val mDiff = currentCalendar.get(Calendar.HOUR_OF_DAY) - hours
            resource.getQuantityString(R.plurals.hours,
                mDiff,
                mDiff)
        }

        else -> {
            val formatter = SimpleDateFormat("dd-MM-YYYY HH:mm", Locale.getDefault())
            return formatter.format(this)
        }
    }

}