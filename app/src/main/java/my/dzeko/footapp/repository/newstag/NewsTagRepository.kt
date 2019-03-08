package my.dzeko.footapp.repository.newstag

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import my.dzeko.footapp.database.AppDatabase
import my.dzeko.footapp.model.entity.NewsTag
import my.dzeko.footapp.model.entity.Tag
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class NewsTagRepository @Inject constructor(
    db: AppDatabase
) {
    private val mNewsTagDao = db.newsTagDao()

    suspend fun saveNewsTag(newsTag: NewsTag) {
        withContext(Dispatchers.IO) {
            mNewsTagDao.insert(newsTag)
        }
    }

    suspend fun getTagsByNewsId(newsId: Long): List<Tag> {
       return  withContext(Dispatchers.IO) {
            mNewsTagDao.getTagsByNewsId(newsId)
        }
    }
}