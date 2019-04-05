package my.dzeko.footapp.model.entity

import android.arch.persistence.room.Entity
import android.arch.persistence.room.Ignore
import android.arch.persistence.room.Index
import android.arch.persistence.room.PrimaryKey
import my.dzeko.newsparser.entities.ParsedTag

@Entity()
data class Tag(
        @PrimaryKey(autoGenerate = true) var id :Long = 0,
        val name :String,
        var isSelected: Boolean = false
) {
        @Ignore
        constructor(parsedTag: ParsedTag): this(
                name = parsedTag.name
        )

        fun switchSelectedState(){
                isSelected = !isSelected
        }
}