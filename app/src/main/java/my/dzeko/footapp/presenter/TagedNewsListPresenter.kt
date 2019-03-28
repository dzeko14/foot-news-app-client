package my.dzeko.footapp.presenter

import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import my.dzeko.footapp.model.entity.NewsSummary
import my.dzeko.footapp.model.entity.Tag
import my.dzeko.footapp.model.interactor.NewsListInteractor
import my.dzeko.footapp.presenter.interfaces.Presenter
import my.dzeko.footapp.view.interfaces.TaggedNewsListView
import javax.inject.Inject

class TagedNewsListPresenter @Inject constructor(
    private val interactor: NewsListInteractor
) : Presenter<TaggedNewsListView>() {

    fun requestNewsList(tagId: Long?) {
        uiScope.launch{
            val tagJob = async{ interactor.getTag(tagId) }
            val newsListJob = async { interactor.getNewsListByTag(tagId) }

            val tag = tagJob.await()
            view?.setNewsListAndTag(newsListJob.await(), tag)
            view?.setSubscribeButtonText(getButtonText(tag.isSelected, view?.subscribeButtonTextArray))
        }
    }

    fun onNewsItemClicked(newsSummary: NewsSummary) {
        view?.navigateToNewsFragment(newsSummary.id)
    }

    private fun onTagClickListener(tag: Tag) {
        uiScope.launch {
            interactor.updateTag(tag)
        }
    }

    fun onSubscribeButtonClick(tag: Tag?) {
        tag?.let {
            tag.switchSelectedState()
            view?.setSubscribeButtonText(getButtonText(tag.isSelected, view?.subscribeButtonTextArray))
            onTagClickListener(tag)
        }
    }

    private fun getButtonText(isSelected: Boolean,
                              subscribeButtonTextArray: Array<CharSequence>?): CharSequence {
        subscribeButtonTextArray?.let {
            return if (isSelected) subscribeButtonTextArray[1]
            else subscribeButtonTextArray[0]
        }
        return ""
    }

    private var scrollRange = -1
    private var isCollapsingToolBarTextShown = true
    fun onAppBarOffsetChanged(totalScrollRange: Int, verticalOffset: Int) {
        if (scrollRange == -1) scrollRange = totalScrollRange

        if (scrollRange + verticalOffset == 0) {
            view?.showTitle()
            isCollapsingToolBarTextShown = true
        } else {
            if (isCollapsingToolBarTextShown) {
                view?.hideTitle()
                isCollapsingToolBarTextShown = false
            }
        }
    }
}