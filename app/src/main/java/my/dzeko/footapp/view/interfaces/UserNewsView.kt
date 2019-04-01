package my.dzeko.footapp.view.interfaces

import android.arch.lifecycle.LiveData
import android.arch.paging.PagedList
import my.dzeko.footapp.interfaces.IEmptiableScreen
import my.dzeko.footapp.interfaces.IListScreen
import my.dzeko.footapp.model.entity.NewsSummary

interface UserNewsView : IEmptiableScreen, IListScreen {
    fun setNewsList(newsList: LiveData<PagedList<NewsSummary>>)
    fun navigateToNewsFragment(id: Long)
}