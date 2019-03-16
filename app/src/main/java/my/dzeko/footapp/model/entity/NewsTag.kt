package my.dzeko.footapp.model.entity

import android.arch.persistence.room.Entity
import android.arch.persistence.room.ForeignKey
import android.arch.persistence.room.Index
import android.arch.persistence.room.PrimaryKey
import my.dzeko.footapp.model.entity.News
import my.dzeko.footapp.model.entity.Tag

@Entity(
    indices = [
        Index(value = ["newsId", "tagId"], unique = true)
    ]
)
data class NewsTag(
    @PrimaryKey(autoGenerate = true) val id: Long,

    @ForeignKey(
        entity = News::class,
        parentColumns = ["id"],
        childColumns = ["newsId"],
        onDelete = ForeignKey.CASCADE
    ) val newsId: Long,

    @ForeignKey(
        entity = Tag::class,
        parentColumns = ["id"],
        childColumns = ["tagId"],
        onDelete = ForeignKey.CASCADE
    ) val tagId: Long
)