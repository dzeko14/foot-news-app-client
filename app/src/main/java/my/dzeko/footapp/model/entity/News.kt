package my.dzeko.footapp.model.entity

import android.arch.persistence.room.Entity
import android.arch.persistence.room.Ignore
import android.arch.persistence.room.PrimaryKey
import my.dzeko.newsparser.entities.ParsedNews

@Entity
data class News(
    @PrimaryKey(autoGenerate = true) var id: Long = 0,
    val title :String,
    val summary :String,
    val date :Long,
    val content :List<String>,
    val images: List<String>,
    val originalUrl :String
){
    @Ignore var tags :List<Tag>? = null

    @Ignore
    constructor(parsedNews: ParsedNews)
            : this(
        title = parsedNews.title,
        content = parsedNews.content,
        date = parsedNews.date,
        images = parsedNews.images,
        originalUrl = parsedNews.originalUrl,
        summary = parsedNews.summary
    )

    companion object {
        fun createEmptyNews(): News {
            return News(
                0,
                "empty",
                "empty",
                0,
                emptyList<String>(),
                emptyList<String>(),
                ""
            )
        }
    }
}