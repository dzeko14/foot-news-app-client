package my.dzeko.footapp.presenter

import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import my.dzeko.footapp.model.entity.Tag
import my.dzeko.footapp.model.interactor.SearchTagInteractor
import my.dzeko.footapp.presenter.interfaces.Presenter
import my.dzeko.footapp.view.interfaces.SearchTagView
import javax.inject.Inject

class SearchTagPresenter @Inject constructor(
    private val interactor: SearchTagInteractor
) : Presenter<SearchTagView>() {
    private var submitQuerySearchJob: Job? = null
    private var changedTextQuerySearchJob: Job? = null

    fun onQueryTextSubmit(name: String) {
        submitQuerySearchJob?.cancel()
        submitQuerySearchJob = uiScope.launch {
            val tags = interactor.getTagsByName(name)
            view?.setTagsList(tags)
            submitQuerySearchJob = null
        }
    }

    fun onQueryTextChanged(newText: String) {
        if (newText.isEmpty()) {
            onEmptyQuery()
        } else {

        }
    }

    fun onEmptyQuery() {
        submitQuerySearchJob?.cancel()
        submitQuerySearchJob = uiScope.launch {
            val tags = interactor.getSelectedTags()
            view?.setTagsList(tags)
            submitQuerySearchJob = null
        }
    }

    fun onTagSelectedClick(tag: Tag) {
        uiScope.launch {
            interactor.updateTag(tag)
        }
    }
}