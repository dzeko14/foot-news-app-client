package my.dzeko.footapp.presenter

import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import my.dzeko.footapp.model.entity.NewsSummary
import my.dzeko.footapp.model.entity.Tag
import my.dzeko.footapp.model.interactor.NewsListInteractor
import my.dzeko.footapp.presenter.interfaces.Presenter
import my.dzeko.footapp.view.interfaces.TagedNewsListView
import javax.inject.Inject

class TagedNewsListPresenter @Inject constructor(
    private val interactor: NewsListInteractor
) : Presenter<TagedNewsListView>() {

    fun requestNewsList(tagId: Long?) {
        uiScope.launch{
            val tag = async{ interactor.getTag(tagId) }
            val newsList = async { interactor.getNewsListByTag(tagId) }
            view?.setNewsListAndTag(newsList.await(), tag.await())
        }
    }

    fun onNewsItemClicked(newsSummary: NewsSummary) {
        view?.navigateToNewsFragment(newsSummary.id)
    }

    fun onTagClickListener(tag: Tag) {
        uiScope.launch {
            interactor.updateTag(tag)
        }
    }
}