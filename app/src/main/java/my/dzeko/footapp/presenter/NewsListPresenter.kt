package my.dzeko.footapp.presenter

import kotlinx.coroutines.launch
import my.dzeko.footapp.model.entity.NewsSummary
import my.dzeko.footapp.model.interactor.NewsListInteractor
import my.dzeko.footapp.presenter.interfaces.Presenter
import my.dzeko.footapp.view.interfaces.NewsListView
import javax.inject.Inject

class NewsListPresenter @Inject constructor(
    private val interactor: NewsListInteractor
) : Presenter<NewsListView>() {
    private var mIsConnectedToNet = false

    fun requestNewsList() {
        uiScope.launch {
            mIsConnectedToNet  = interactor.getInternetConnectionStatus()
            checkLoading()
            val newsList = interactor.getNewsList()
            view?.setNewsList(newsList)
        }
    }

    private fun checkLoading() {
        val size = view?.itemsCount ?: return
        if (mIsConnectedToNet && size == 0) view?.showLoading()
    }

    fun onNewsItemClicked(newsSummary: NewsSummary) {
        view?.navigateToNewsFragment(newsSummary.id)
    }

    fun onNewsListSizeCheck() {
        val size = view?.itemsCount ?: return
        if (size == 0 && !mIsConnectedToNet) {
            view?.showEmptyScreen()
            view?.hideLoading()
        }
        else {
            view?.hideEmptyScreen()
            view?.hideLoading()
        }
    }
}