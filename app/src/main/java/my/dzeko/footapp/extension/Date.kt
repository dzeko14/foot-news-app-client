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
    calendar.time = this
    val minutes = calendar.get(Calendar.MINUTE)
    val hours = calendar.get(Calendar.HOUR_OF_DAY)
    return when {
        diff <= HOUR_IN_MILLS -> resource.getQuantityString(R.plurals.minutes, minutes, minutes)

        diff <= DAY_IN_MILLS -> resource.getQuantityString(R.plurals.hours, hours, hours)

        else -> {
            val formatter = SimpleDateFormat("dd-MM-YYYY HH:mm", Locale.getDefault())
            return formatter.format(this)
        }
    }

}