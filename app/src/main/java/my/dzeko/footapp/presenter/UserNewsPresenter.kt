package my.dzeko.footapp.presenter

import kotlinx.coroutines.launch
import my.dzeko.footapp.model.entity.NewsSummary
import my.dzeko.footapp.model.interactor.UserNewsListInteractor
import my.dzeko.footapp.presenter.interfaces.Presenter
import my.dzeko.footapp.view.interfaces.UserNewsView
import javax.inject.Inject

class UserNewsPresenter @Inject constructor(
    private val interactor: UserNewsListInteractor
) : Presenter<UserNewsView>() {
    fun requestNewsList() {
        uiScope.launch {
            val newsList = interactor.getUserNewsList()
            view?.setNewsList(newsList)
        }
    }

    fun onNewsItemClicked(newsSummary: NewsSummary) {
        view?.navigateToNewsFragment(newsSummary.id)
    }

    fun onNewsListSizeCheck() {
        val size = view?.itemsCount ?: return
        if (size == 0) view?.showEmptyScreen()
        else view?.hideEmptyScreen()
    }
}