package my.dzeko.footapp.repository.tag

import android.arch.lifecycle.LiveData
import android.arch.paging.LivePagedListBuilder
import android.arch.paging.PagedList
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

    suspend fun getTagByNamePart(name: String): LiveData<PagedList<Tag>> {
        return withContext(Dispatchers.IO) {
            LivePagedListBuilder(mTagDao.getByNamePart(name), 20).build()
        }
    }

    suspend fun getSelectedTags(): LiveData<PagedList<Tag>> {
        return withContext(Dispatchers.IO) {
            LivePagedListBuilder(mTagDao.getSelected(), 20).build()
        }
    }
}