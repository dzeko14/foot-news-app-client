package my.dzeko.footapp.repository.news

import android.arch.lifecycle.LiveData
import android.arch.paging.DataSource
import android.arch.paging.LivePagedListBuilder
import android.arch.paging.PagedList
import my.dzeko.footapp.database.AppDatabase
import my.dzeko.footapp.model.entity.News
import my.dzeko.footapp.model.entity.NewsSummary
import org.joda.time.DateTime
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class LocalNewsRepository @Inject constructor(
    db: AppDatabase
) {
    private val mNewsDao = db.newsDao()

    fun getNews(): LiveData<PagedList<NewsSummary>> {
       return LivePagedListBuilder(mNewsDao.getAllNewsSummary(), 20).build()
    }

    fun save(news: News): News {
        val id = mNewsDao.insert(news)
        news.id = id
        return news
    }

    fun getNewsById(id: Long): News {
        return mNewsDao.getById(id)
    }

    fun getNewsByTagId(tagId: Long): LiveData<PagedList<NewsSummary>> {
        return LivePagedListBuilder(mNewsDao.getNewsByTagId(tagId), 20).build()
    }

    fun getUserNews(): LiveData<PagedList<NewsSummary>> {
        return LivePagedListBuilder(mNewsDao.getUserNews(), 20).build()
    }

    fun getLastAddedNewsTime(): DateTime {
        val mills = (mNewsDao.getLatestNewsDate() ?: 0) + 1
        return DateTime(mills)
    }

}