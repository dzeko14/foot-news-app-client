package my.dzeko.footapp.presenter

import kotlinx.coroutines.launch
import my.dzeko.footapp.model.interactor.NewsInteractor
import my.dzeko.footapp.presenter.interfaces.Presenter
import my.dzeko.footapp.view.interfaces.NewsView
import javax.inject.Inject

class NewsPresenter @Inject constructor(
    private val interactor: NewsInteractor
) : Presenter<NewsView>() {
    fun requestNews(newsId: Long) {
        uiScope.launch {
            view?.showLoading()
            val news = interactor.getNewsById(newsId)
            view?.setNews(news)
            view?.hideLoading()
        }
    }

}