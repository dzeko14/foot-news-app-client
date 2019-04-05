package my.dzeko.footapp.model.interactor

import kotlinx.coroutines.*
import my.dzeko.footapp.repository.news.NewsRepository
import javax.inject.Inject

const val SPLASH_SCREEN_TIME = 1500L

class SplashScreenInteractor @Inject constructor(
    private val mNewsRepository: NewsRepository
) {

    suspend fun requestNewsUpdate() {
        mNewsRepository.startNewsUpdate()
        delay(SPLASH_SCREEN_TIME)
    }
}