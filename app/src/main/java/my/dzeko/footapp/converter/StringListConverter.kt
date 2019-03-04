package my.dzeko.footapp.converter

import android.arch.persistence.room.TypeConverter

private const val SPLITTER = "|||"

class StringListConverter {
    @TypeConverter
    fun convertToString(stringList: List<String>): String {
        if (stringList.isEmpty()) return ""
        return stringList.reduce { result, item ->
            return "$result$SPLITTER$item"
        }
    }

    @TypeConverter
    fun convertToStringList(str: String): List<String> {
        if (str.isEmpty()) return emptyList()
        return str.split(SPLITTER)
    }
}