package my.dzeko.footapp.model.entity

import android.arch.persistence.room.Entity
import android.arch.persistence.room.Ignore
import android.arch.persistence.room.PrimaryKey

@Entity
data class News(
    @PrimaryKey var id: Long = 0,
    var title :String = "",
    var summary :String = "",
    var date :Long = 0,
    var content :List<String> = emptyList(),
    var images: List<String> = emptyList(),
    var originalUrl :String = ""
){
    @Ignore var tags :List<Tag>? = null
}