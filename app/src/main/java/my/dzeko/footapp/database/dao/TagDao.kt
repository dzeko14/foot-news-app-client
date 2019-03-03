package my.dzeko.footapp.database.dao

import android.arch.persistence.room.*
import my.dzeko.footapp.model.entity.Tag

@Dao
interface TagDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insert(tag: Tag)

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insert(tagList: List<Tag>)

    @Delete
    fun delete(tag: Tag)

    @Query("Select * From Tag Where id = :id")
    fun getById(id: Int): Tag
}