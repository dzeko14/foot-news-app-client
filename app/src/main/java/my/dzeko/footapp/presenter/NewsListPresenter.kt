package my.dzeko.footapp.presenter

import my.dzeko.footapp.model.interactor.NewsListInteractor
import my.dzeko.footapp.presenter.interfaces.Presenter
import my.dzeko.footapp.view.interfaces.NewsListView
import javax.inject.Inject

class NewsListPresenter @Inject constructor(
    private val interactor: NewsListInteractor
) : Presenter<NewsListView>() {
    fun requestNewsList() {

    }
}