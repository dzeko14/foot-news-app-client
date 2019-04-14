package my.dzeko.footapp.view.interfaces

import android.arch.lifecycle.LiveData
import android.arch.paging.PagedList
import android.database.Cursor
import my.dzeko.footapp.model.entity.Tag

interface SearchTagView {
    fun setTagsList(tags: LiveData<PagedList<Tag>>)
    fun hideKeyBoard()
}