package my.dzeko.footapp.repository.tag

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import my.dzeko.footapp.database.AppDatabase
import my.dzeko.footapp.model.entity.Tag
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class TagsRepository @Inject constructor(
    db: AppDatabase
) {
    private val mTagDao = db.tagDao()

    suspend fun saveTags(tags: List<Tag>?) {
        withContext(Dispatchers.IO) {
            tags?.let { mTagDao.insert(tags) }

        }
    }

    suspend fun getTagById(tagId: Long): Tag {
        return withContext(Dispatchers.IO) {
            mTagDao.getById(tagId)
        }
    }

    suspend fun updateTag(tag: Tag) {
        withContext(Dispatchers.IO) {
            mTagDao.insert(tag)
        }
    }
}