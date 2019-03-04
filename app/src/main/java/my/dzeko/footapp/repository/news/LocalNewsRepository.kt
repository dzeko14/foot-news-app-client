package my.dzeko.footapp.repository.news

import android.arch.lifecycle.LiveData
import android.arch.paging.LivePagedListBuilder
import android.arch.paging.PagedList
import my.dzeko.footapp.database.AppDatabase
import my.dzeko.footapp.model.entity.News
import my.dzeko.footapp.model.entity.NewsSummary
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class LocalNewsRepository @Inject constructor(
    db: AppDatabase
) {
    private val newsDao = db.newsDao()

    fun getNews(): LiveData<PagedList<NewsSummary>> {
       return LivePagedListBuilder(newsDao.getAllNewsSummary(), 50).build()
    }

    fun save(news: News?) {
        news?.let { newsDao.insert(news) }
    }
}