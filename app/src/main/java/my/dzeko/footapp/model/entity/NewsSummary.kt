package my.dzeko.footapp.model.entity

import org.joda.time.DateTime

data class NewsSummary(
    val id: Long,
    val title :String,
    val summary :String,
    val date: DateTime
)