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
    private var mPreviousNewsListSize: Int? = null

    fun requestNewsList() {
        uiScope.launch {
            view?.showLoading()
            mIsConnectedToNet  = interactor.getInternetConnectionStatus()
            val newsList = interactor.getNewsList()
            view?.setNewsList(newsList)
        }
    }

    fun onNewsItemClicked(newsSummary: NewsSummary) {
        view?.navigateToNewsFragment(newsSummary.id)
    }

    fun onNewsListUpdate() {
        uiScope.launch {
            val internetAvailable = interactor.getInternetConnectionStatus()
            if (internetAvailable) {
                interactor.updateNewsList()
            }
            view?.hideLoading()
        }
    }

    fun onNewsListSizeCheck(size: Int) {
        if (size == 0) {
            if (!mIsConnectedToNet) {
                view?.showEmptyScreen()
                view?.hideLoading()
            }
        }
        else {
            view?.hideEmptyScreen()
            view?.hideLoading()
            if (mPreviousNewsListSize == null) {
                mPreviousNewsListSize = size
            } else {
                if (mPreviousNewsListSize != size) {
                    view?.showItemsUpdated()
                    mPreviousNewsListSize = size
                }
            }
        }
    }
}