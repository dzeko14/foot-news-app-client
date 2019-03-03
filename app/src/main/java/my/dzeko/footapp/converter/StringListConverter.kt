package my.dzeko.footapp.converter

import android.arch.persistence.room.TypeConverter

private const val SPLITTER = "|||"

class StringListConverter {
    @TypeConverter
    fun convertToString(stringList: List<String>): String {
        return stringList.reduce { result, item ->
            return "$result$SPLITTER$item"
        }
    }

    @TypeConverter
    fun convertToStringList(str: String): List<String> {
        return str.split(SPLITTER)
    }
}