package my.dzeko.footapp.command

import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.coroutineScope
import my.dzeko.footapp.model.entity.News
import my.dzeko.footapp.model.entity.NewsTag
import my.dzeko.footapp.model.entity.Tag
import my.dzeko.footapp.repository.news.NewsRepository
import my.dzeko.footapp.repository.newstag.NewsTagRepository
import my.dzeko.footapp.repository.tag.TagsRepository
import my.dzeko.newsparser.NewsParser

class ParseNewsCommand(
    private val mNewsRepo: NewsRepository,
    private val mTagRepo: TagsRepository,
    private val mNewsParser: NewsParser,
    private val mTagNewsRepo: NewsTagRepository
) {
    suspend fun execute() = coroutineScope {
        val lastParsedNewsTime = mNewsRepo.getLastAddedNewsTime()

        val parsedNews = mNewsParser.parseNews(lastParsedNewsTime)

        if (parsedNews.isEmpty()) {

        } else {
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
        }
    }

}