package my.dzeko.footapp.model.interactor

import android.arch.lifecycle.LiveData
import android.arch.paging.PagedList
import com.google.firebase.database.ChildEventListener
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import kotlinx.coroutines.*
import my.dzeko.footapp.model.entity.News
import my.dzeko.footapp.model.entity.NewsSummary
import my.dzeko.footapp.model.entity.NewsTag
import my.dzeko.footapp.repository.news.NewsRepository
import my.dzeko.footapp.repository.newstag.NewsTagRepository
import my.dzeko.footapp.repository.tag.TagsRepository
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class NewsListInteractor @Inject constructor(
    private val mNewsRepo: NewsRepository,
    private val mTagsRepo: TagsRepository,
    private val mNewsTagRepository: NewsTagRepository
) {
    private val mNewsListChangesListener = object : NewsListChangesListener(){
        private val ioScope = CoroutineScope(Dispatchers.IO)

        override fun onChildChanged(dataSnapshot: DataSnapshot, p1: String?) {
            ioScope.launch {
                val news = dataSnapshot.getValue(News::class.java)
                mNewsRepo.save(news)
            }
        }

        override fun onChildAdded(dataSnapshot: DataSnapshot, p1: String?) {
            ioScope.launch {
                val news = dataSnapshot.getValue(News::class.java)
                mNewsRepo.save(news)
                news?.let {
                    mTagsRepo.saveTags(news.tags)
                    resolveNewsTags(news)
                }
            }
        }

        private suspend fun resolveNewsTags(news: News) {
            news.tags?.let { tags ->
                for (tag in tags) {
                    val newsTag = NewsTag(0, news.id, tag.id)
                    mNewsTagRepository.saveNewsTag(newsTag)
                }
            }
        }
    }

    suspend fun getNewsList(): LiveData<PagedList<NewsSummary>> {
        return mNewsRepo.getNews(mNewsListChangesListener)
    }


    abstract class NewsListChangesListener : ChildEventListener {
        override fun onChildMoved(p0: DataSnapshot, p1: String?) { /*Empty method*/ }

        override fun onChildRemoved(p0: DataSnapshot) { /*Empty method*/ }

        override fun onCancelled(error: DatabaseError) { /*Empty method*/ }
    }
}
