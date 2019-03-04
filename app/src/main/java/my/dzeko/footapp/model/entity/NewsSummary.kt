package my.dzeko.footapp.model.entity

import java.util.*

data class NewsSummary(
    val id: Long,
    val title :String,
    val summary :String,
    val date: Date
)