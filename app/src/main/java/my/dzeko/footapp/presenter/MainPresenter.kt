package my.dzeko.footapp.presenter

import androidx.navigation.NavController
import my.dzeko.footapp.R
import my.dzeko.footapp.presenter.interfaces.Presenter
import my.dzeko.footapp.view.interfaces.MainView
import javax.inject.Inject

class MainPresenter @Inject constructor() : Presenter<MainView>() {
    private var mIsBackPressed = false

    val destinationChangedListener
            = NavController.OnDestinationChangedListener { _, destination, _ ->
        if (mIsBackPressed) {
            mIsBackPressed = false
            when(destination.id) {
                R.id.newsListFragment -> view?.setBottomNaViewItem(R.id.newsListFragment)
                R.id.userNewsListFragment -> view?.setBottomNaViewItem(R.id.userNewsListFragment)
            }
        }
    }

    fun onBottomNavViewVisibility(isVisible: Boolean) {
        if (isVisible) view?.showBottomNavView()
        else view?.hideBottomNavView()
    }

    fun onBackPressed() {
        mIsBackPressed = true
    }

}