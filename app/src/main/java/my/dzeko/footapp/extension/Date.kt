package my.dzeko.footapp.extension

import java.text.SimpleDateFormat
import java.util.*

fun Date.formattedTime(): String {
    val formatter = SimpleDateFormat("hh:mm")
    return formatter.format(this)
}