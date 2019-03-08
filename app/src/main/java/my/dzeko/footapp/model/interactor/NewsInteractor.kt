package my.dzeko.footapp.model.interactor

import my.dzeko.footapp.model.entity.News
import my.dzeko.footapp.repository.news.NewsRepository
import my.dzeko.footapp.repository.newstag.NewsTagRepository
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class NewsInteractor @Inject constructor(
    private val newsRepository: NewsRepository,
    private val newsTagRepository: NewsTagRepository
) {
    suspend fun getNewsById(newsId: Long): News {
        val news = newsRepository.getNewsById(newsId)
        val tags = newsTagRepository.getTagsByNewsId(newsId)
        news.tags = tags
        return news
    }
}