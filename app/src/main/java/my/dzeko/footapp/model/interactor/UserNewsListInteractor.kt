package my.dzeko.footapp.model.interactor

import android.arch.lifecycle.LiveData
import android.arch.paging.PagedList
import my.dzeko.footapp.model.entity.NewsSummary
import my.dzeko.footapp.repository.news.NewsRepository
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class UserNewsListInteractor @Inject constructor(
    private val mNewsRepo: NewsRepository
) {
    suspend fun getUserNewsList(): LiveData<PagedList<NewsSummary>> {
        return mNewsRepo.getUserNews()
    }
}