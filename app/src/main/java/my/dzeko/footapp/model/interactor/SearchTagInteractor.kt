package my.dzeko.footapp.model.interactor

import android.arch.lifecycle.LiveData
import android.arch.paging.PagedList
import my.dzeko.footapp.model.entity.Tag
import my.dzeko.footapp.repository.tag.TagsRepository
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class SearchTagInteractor @Inject constructor(
    private val tagRepo: TagsRepository
) {
    suspend fun getTagsByName(name: String): LiveData<PagedList<Tag>> {
        return tagRepo.getTagByNamePart("%$name%")
    }

    suspend fun getSelectedTags(): LiveData<PagedList<Tag>> {
        return tagRepo.getSelectedTags()
    }

    suspend fun updateTag(tag: Tag) {
        tagRepo.updateTag(tag)
    }
}