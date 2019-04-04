package my.dzeko.footapp.database.dao

import android.arch.paging.DataSource
import android.arch.persistence.room.*
import my.dzeko.footapp.model.entity.News
import my.dzeko.footapp.model.entity.NewsSummary

@Dao
interface NewsDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(news: News)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(newsList: List<News>)

    @Delete
    fun delete(news: News)

    @Delete
    fun delete(newsList: List<News>)

    @Query("Select * From ParsedNews Where id = :id")
    fun getById(id: Long): News

    @Query("Select title, id, summary, date From ParsedNews Order By date Desc")
    fun getAllNewsSummary(): DataSource.Factory<Int, NewsSummary>

    @Query("Select date From ParsedNews Order By date Desc Limit 1")
    fun getLatestNewsDate(): Long?

    @Query("Select title, T1.id, summary, date From ParsedNews As T1 Inner Join NewsTag as T2 On T1.id=T2.newsId Where T2.tagId=:tagId Order By date Desc")
    fun getNewsByTagId(tagId: Long): DataSource.Factory<Int, NewsSummary>

    @Query("Select Distinct title, T1.id, summary, date From ParsedNews As T1 Inner Join NewsTag as T2 On T1.id=T2.newsId Inner Join ParsedTag As T3 On T3.id=T2.tagId Where T3.isSelected = 1 Order By date Desc ")
    fun getUserNews(): DataSource.Factory<Int, NewsSummary>
}