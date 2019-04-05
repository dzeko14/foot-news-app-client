package my.dzeko.footapp.presenter

import kotlinx.coroutines.launch
import my.dzeko.footapp.model.interactor.SplashScreenInteractor
import my.dzeko.footapp.presenter.interfaces.Presenter
import my.dzeko.footapp.view.interfaces.SplashScreenView
import javax.inject.Inject

class SplashScreenPresenter @Inject constructor(
    val mInteractor: SplashScreenInteractor
) : Presenter<SplashScreenView>() {

    fun onFragmentStarted() = uiScope.launch {
        mInteractor.requestNewsUpdate()
        view?.navigateToListFragment()
    }
}