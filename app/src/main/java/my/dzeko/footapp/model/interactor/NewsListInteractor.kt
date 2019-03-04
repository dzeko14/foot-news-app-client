package my.dzeko.footapp.model.interactor

import android.arch.lifecycle.LiveData
import android.arch.paging.PagedList
import kotlinx.coroutines.*
import my.dzeko.footapp.model.entity.NewsSummary
import my.dzeko.footapp.repository.news.NewsRepository
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class NewsListInteractor @Inject constructor(
    private val newsRepo: NewsRepository
) {

    suspend fun getNewsList(): LiveData<PagedList<NewsSummary>> {
        return newsRepo.getNews()
    }
}
