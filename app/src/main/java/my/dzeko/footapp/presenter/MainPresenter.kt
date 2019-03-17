package my.dzeko.footapp.presenter

import my.dzeko.footapp.presenter.interfaces.Presenter
import my.dzeko.footapp.view.interfaces.MainView
import javax.inject.Inject

class MainPresenter @Inject constructor() : Presenter<MainView>() {
    fun onBottomNavViewVisibility(isVisible: Boolean) {
        if (isVisible) view?.showBottomNavView()
        else view?.hideBottomNavView()
    }
}