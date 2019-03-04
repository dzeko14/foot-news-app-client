package my.dzeko.footapp.model.entity

import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey

@Entity
data class Tag(
        @PrimaryKey var id :Int = 0,
        var name :String = ""
)