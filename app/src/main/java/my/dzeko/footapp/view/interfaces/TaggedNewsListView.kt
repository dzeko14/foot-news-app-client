package my.dzeko.footapp.view.interfaces

import android.arch.lifecycle.LiveData
import android.arch.paging.PagedList
import my.dzeko.footapp.model.entity.NewsSummary
import my.dzeko.footapp.model.entity.Tag

interface TaggedNewsListView {
    fun setNewsListAndTag(newsList: LiveData<PagedList<NewsSummary>>, tag: Tag)
    fun navigateToNewsFragment(id: Long)
    fun setSubscribeButtonText(text: CharSequence)
    val subscribeButtonTextArray: Array<CharSequence>?
    fun showTitle()
    fun hideTitle()
}