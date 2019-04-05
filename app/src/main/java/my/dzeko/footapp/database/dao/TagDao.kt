package my.dzeko.footapp.database.dao

import android.arch.paging.DataSource
import android.arch.persistence.room.*
import my.dzeko.footapp.model.entity.Tag

@Dao
interface TagDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(tag: Tag)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(tagList: List<Tag>): List<Long>

    @Delete
    fun delete(tag: Tag)

    @Query("Select * From Tag Where id = :id")
    fun getById(id: Long): Tag

    @Query("Select * From Tag Where name Like :name Order By name")
    fun getByNamePart(name: String): DataSource.Factory<Int, Tag>

    @Query("Select * From Tag Where isSelected = 1 Order By name")
    fun getSelected(): DataSource.Factory<Int, Tag>
}