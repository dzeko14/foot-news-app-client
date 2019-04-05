package my.dzeko.footapp.worker

import android.content.Context
import androidx.work.CoroutineWorker
import androidx.work.Worker
import androidx.work.WorkerParameters
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.coroutineScope
import my.dzeko.footapp.application.FootApplication
import my.dzeko.footapp.database.AppDatabase
import my.dzeko.footapp.model.entity.News
import my.dzeko.footapp.model.entity.NewsTag
import my.dzeko.footapp.model.entity.Tag
import my.dzeko.footapp.repository.news.NewsRepository
import my.dzeko.footapp.repository.newstag.NewsTagRepository
import my.dzeko.footapp.repository.tag.TagsRepository
import my.dzeko.newsparser.NewsParser
import javax.inject.Inject

class ParsingNewsWorker(
    appContext: Context,
    workerParams: WorkerParameters
) : CoroutineWorker(appContext, workerParams)  {

    @Inject lateinit var mNewsRepo: NewsRepository
    @Inject lateinit var mTagRepo: TagsRepository
    @Inject lateinit var mNewsParser: NewsParser
    @Inject lateinit var mTagNewsRepo: NewsTagRepository

    override val coroutineContext = Dispatchers.IO

    init {
        val app = appContext as FootApplication
        app.appComponent.provideParsingNewsWorker(this)
    }

    override suspend fun doWork(): Result = coroutineScope {
        val lastParsedNewsTime = mNewsRepo.getLastAddedNewsTime()

        val parsedNews = mNewsParser.parseNews(lastParsedNewsTime)

        val newsListJob = parsedNews.map { pNews ->
            async {
                var news = News(pNews)
                var tags = pNews.parsedTags.map { t -> Tag(t.name.hashCode().toLong(), t.name) }

                news = mNewsRepo.save(news)
                tags = mTagRepo.saveTags(tags)

                val tagNewsSaveJob = tags.map { t ->
                    async {
                        mTagNewsRepo.saveNewsTag(NewsTag(newsId = news.id, tagId = t.id))
                    }
                }

                tagNewsSaveJob.awaitAll()
            }
        }

        newsListJob.awaitAll()

         Result.success()
    }
}