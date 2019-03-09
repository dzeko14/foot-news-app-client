package my.dzeko.footapp.presenter

import kotlinx.coroutines.launch
import my.dzeko.footapp.model.entity.NewsSummary
import my.dzeko.footapp.model.interactor.NewsListInteractor
import my.dzeko.footapp.presenter.interfaces.Presenter
import my.dzeko.footapp.view.fragment.FIND_BY_TAG_ACTION
import my.dzeko.footapp.view.interfaces.NewsListView
import javax.inject.Inject

class NewsListPresenter @Inject constructor(
    private val interactor: NewsListInteractor
) : Presenter<NewsListView>() {


    fun requestNewsList(actionType: Int) {
        uiScope.launch {
            val newsList = when(actionType) {
                FIND_BY_TAG_ACTION -> interactor.getNewsListByTag(view?.tagId)

                else -> interactor.getNewsList()
            }
            view?.setNewsList(newsList)
        }
    }

    fun onNewsItemClicked(newsSummary: NewsSummary) {
        view?.navigateToNewsFragment(newsSummary.id)
    }
}