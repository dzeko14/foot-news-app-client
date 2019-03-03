package my.dzeko.footapp.model.interactor

import android.arch.lifecycle.LiveData
import android.arch.paging.PagedList
import my.dzeko.footapp.model.entity.NewsSummary
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class NewsListInteractor @Inject constructor() {

    fun getNewsList(callback: Callback) {

    }

    interface Callback{
        fun onSuccess(list: LiveData<PagedList<NewsSummary>>)
        fun onFail(msg: String)
    }
}
