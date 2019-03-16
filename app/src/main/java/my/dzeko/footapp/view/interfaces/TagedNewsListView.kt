package my.dzeko.footapp.view.interfaces

import android.arch.lifecycle.LiveData
import android.arch.paging.PagedList
import my.dzeko.footapp.model.entity.NewsSummary
import my.dzeko.footapp.model.entity.Tag

interface TagedNewsListView {
    fun setNewsList(newsList: LiveData<PagedList<NewsSummary>>)
    fun setTag(tag: Tag)
    fun navigateToNewsFragment(id: Long)

}