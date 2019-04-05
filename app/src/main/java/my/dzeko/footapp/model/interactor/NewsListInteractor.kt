package my.dzeko.footapp.model.interactor

import android.arch.lifecycle.LiveData
import android.arch.paging.PagedList
import kotlinx.coroutines.*
import my.dzeko.footapp.manager.ConnectionManager
import my.dzeko.footapp.model.entity.News
import my.dzeko.footapp.model.entity.NewsSummary
import my.dzeko.footapp.model.entity.NewsTag
import my.dzeko.footapp.model.entity.Tag
import my.dzeko.footapp.repository.news.NewsRepository
import my.dzeko.footapp.repository.newstag.NewsTagRepository
import my.dzeko.footapp.repository.tag.TagsRepository
import java.lang.Exception
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class NewsListInteractor @Inject constructor(
    private val mNewsRepo: NewsRepository,
    private val mTagsRepo: TagsRepository,
    private val mNewsTagRepository: NewsTagRepository,
    private val mConnectionManager: ConnectionManager
) {
    suspend fun getNewsList(): LiveData<PagedList<NewsSummary>> {
        return mNewsRepo.getNews()
    }

    suspend fun getNewsListByTag(tagId: Long?): LiveData<PagedList<NewsSummary>> {
        tagId?.let {
            return mNewsRepo.getNewsByTagId(tagId)
        }

        throw Exception("tagId is null!")
    }

    suspend fun getTag(tagId: Long?): Tag {
        tagId?.let {
            return mTagsRepo.getTagById(tagId)
        }

        throw Exception("tagId is null!")
    }

    suspend fun updateTag(tag: Tag) {
        mTagsRepo.updateTag(tag)
    }

    fun getInternetConnectionStatus(): Boolean {
        return mConnectionManager.isConnectedToInternet
    }

}
