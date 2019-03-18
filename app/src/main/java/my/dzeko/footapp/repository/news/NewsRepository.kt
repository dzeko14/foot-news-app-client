package my.dzeko.footapp.repository.news

import android.arch.lifecycle.LiveData
import android.arch.paging.PagedList
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import my.dzeko.footapp.model.entity.News
import my.dzeko.footapp.model.entity.NewsSummary
import my.dzeko.footapp.model.interactor.NewsListInteractor
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class NewsRepository @Inject constructor(
    private val mLocalRepo: LocalNewsRepository,
    private val mNetworkRepo: NetworkNewsRepository
) {
    suspend fun getNews(newsListChangesListener: NewsListInteractor.NewsListChangesListener)
            : LiveData<PagedList<NewsSummary>> {
        return withContext(Dispatchers.IO) {
            val lastAddNewsDate = mLocalRepo.getLastAddedNewsDate() ?: 0
            mNetworkRepo.addNewsListener(newsListChangesListener, lastAddNewsDate)
             mLocalRepo.getNews()
        }
    }

    suspend fun getNewsById(id: Long): News {
        return withContext(Dispatchers.IO) {
             mLocalRepo.getNewsById(id)
        }
    }

    fun save(news: News?) {
        mLocalRepo.save(news)
    }

    suspend fun getNewsByTagId(tagId: Long): LiveData<PagedList<NewsSummary>> {
        return withContext(Dispatchers.IO) {
            mLocalRepo.getNewsByTagId(tagId)
        }
    }

    suspend fun getUserNews(): LiveData<PagedList<NewsSummary>> {
        return withContext(Dispatchers.IO) {
            mLocalRepo.getUserNews()
        }
    }


}