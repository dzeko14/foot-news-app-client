package my.dzeko.footapp.repository.news

import android.arch.lifecycle.LiveData
import android.arch.paging.PagedList
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import my.dzeko.footapp.model.entity.News
import my.dzeko.footapp.model.entity.NewsSummary
import org.joda.time.DateTime
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class NewsRepository @Inject constructor(
    private val mLocalRepo: LocalNewsRepository,
    private val mNetworkRepo: NetworkNewsRepository
) {
    suspend fun getNews()
            : LiveData<PagedList<NewsSummary>> {
        return withContext(Dispatchers.IO) {
             mLocalRepo.getNews()
        }
    }

    suspend fun startNewsUpdate() = withContext(Dispatchers.IO) {
        mNetworkRepo.startNewsUpdate()
    }

    suspend fun getNewsById(id: Long): News {
        return withContext(Dispatchers.IO) {
             mLocalRepo.getNewsById(id)
        }
    }

    fun save(news: News): News {
        return mLocalRepo.save(news)
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

    suspend fun getLastAddedNewsTime(): DateTime {
        return withContext(Dispatchers.IO) {
            mLocalRepo.getLastAddedNewsTime()
        }
    }

    suspend fun removeNews(news: News) = withContext(Dispatchers.IO) {
        mLocalRepo.remove(news)
    }

}