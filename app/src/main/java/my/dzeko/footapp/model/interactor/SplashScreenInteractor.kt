package my.dzeko.footapp.model.interactor

import kotlinx.coroutines.*
import javax.inject.Inject

const val SPLASH_SCREEN_TIME = 1500L

class SplashScreenInteractor @Inject constructor() {
    private val splashScreenJob: Job = Job()
    private val uiScope = CoroutineScope(Dispatchers.Main + splashScreenJob)

    fun waitSomeTime(callback: () -> Unit) {
        uiScope.launch {
            delay(SPLASH_SCREEN_TIME)
            callback()
        }
    }

}