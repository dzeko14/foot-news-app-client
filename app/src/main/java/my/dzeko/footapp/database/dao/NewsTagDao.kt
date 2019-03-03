package my.dzeko.footapp.database.dao

import android.arch.persistence.room.Dao
import android.arch.persistence.room.Insert
import android.arch.persistence.room.Query
import my.dzeko.footapp.model.entity.NewsTag
import my.dzeko.footapp.model.entity.Tag

@Dao
interface NewsTagDao {
    @Insert
    fun insert(nt: NewsTag)

    @Insert
    fun insert(ntList: List<NewsTag>)

    @Query("Select T2.id, T2.name From NewsTag As T1 Inner Join Tag As T2 On T1.tagId=T2.id Where T1.newsId = :newsId")
    fun getTagsByNewsId(newsId: Long): List<Tag>
}