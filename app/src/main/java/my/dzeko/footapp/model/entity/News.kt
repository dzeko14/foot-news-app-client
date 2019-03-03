package my.dzeko.footapp.model.entity

import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey

@Entity
data class News(
    @PrimaryKey var id: Long,
    var title :String,
    var summary :String,
    var date :Long,
    var tags :List<Tag>,
    var content :List<String>,
    var images: List<String>,
    var originalUrl :String
) {

}