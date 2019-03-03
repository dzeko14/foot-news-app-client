package my.dzeko.footapp.model.entity

import android.arch.persistence.room.Entity
import android.arch.persistence.room.Ignore
import android.arch.persistence.room.PrimaryKey

@Entity
data class News(
    @PrimaryKey val id: Long,
    val title :String,
    val summary :String,
    val date :Long,
    val content :List<String>,
    val images: List<String>,
    val originalUrl :String
){
    @Ignore var tags :List<Tag>? = null
}