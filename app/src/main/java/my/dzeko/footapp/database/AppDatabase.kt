package my.dzeko.footapp.database

import android.arch.persistence.room.Database
import android.arch.persistence.room.RoomDatabase
import android.arch.persistence.room.TypeConverters
import my.dzeko.footapp.converter.StringListConverter
import my.dzeko.footapp.database.dao.NewsDao
import my.dzeko.footapp.database.dao.NewsTagDao
import my.dzeko.footapp.database.dao.TagDao
import my.dzeko.footapp.model.entity.News
import my.dzeko.footapp.model.entity.NewsTag
import my.dzeko.footapp.model.entity.Tag
import javax.inject.Singleton

@Singleton
@Database(
    entities = [
        News::class,
        Tag::class,
        NewsTag::class
    ],
    version = 1,
    exportSchema = false
)
@TypeConverters(value = [
    StringListConverter::class
])
abstract class AppDatabase : RoomDatabase() {
    abstract fun newsDao(): NewsDao
    abstract fun tagDao(): TagDao
    abstract fun newsTagDao(): NewsTagDao
}