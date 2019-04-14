package my.dzeko.newsparser.utils

import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.coroutineScope
import my.dzeko.newsparser.entities.ParsedNews
import my.dzeko.newsparser.entities.ParsedTag
import org.joda.time.DateTime
import org.jsoup.nodes.Document
import org.jsoup.nodes.Element
import java.lang.StringBuilder
import java.util.*

internal object NewsParserUtils {

    suspend fun parseNews(doc :Document, lastParsedNewsTime: DateTime): List<ParsedNews> {
        return coroutineScope {
            val newsElements = doc.getElementsByClass("short-news")
            val newsBlocks = newsElements[0]

            val dateString: String = newsBlocks.child(0).text()
            val dateAndTime = DateAndTimeUtils.getDateFromString(dateString)

            val newsList = Collections.synchronizedList(mutableListOf<ParsedNews>())

            val jobs = newsBlocks.children().map {elem ->
                async {
                    if (isTagWithAds(elem) || isTagWithBrake(elem) || isTagWithDate(elem)) return@async

                    val time = elem.getElementsByClass("time")
                        .first()
                        .text()

                    if (DateAndTimeUtils
                            .getDateWithTime(time, dateAndTime)
                            .millis < lastParsedNewsTime.millis) return@async

                    val news = parseNewsContent(elem, dateAndTime)
                    news?.let {
                        newsList.add(news)
                    }
                }
            }
            jobs.awaitAll()
            newsList
        }
    }

    private fun isTagWithBrake(element: Element) :Boolean {
        return element.tagName() == "br"
    }

    private fun isTagWithDate(element: Element) :Boolean {
        return element.tagName() == "b"
    }

    private fun isTagWithAds(element: Element) :Boolean {
        return element.tagName() == "div"
    }

    fun parseNewsContent(elem: Element, localDateTime: DateTime) : ParsedNews?  {
        if (elem.getElementsByClass("live").first() != null) return null

        val timeStr = elem.getElementsByClass("time").first().text()
        val date = DateAndTimeUtils.getDateWithTime(timeStr, localDateTime)
        val content = elem.getElementsByClass("short-text").first()

        val title = content.text()
        val summary = content.attr("title")

        val resource = content.attr("href")
        if (resource.contains("https://")) return null

        val document = NewsParserNetworkUtils.getNewsConentDocument(resource)

        val article = document.getElementsByTag("article").first()

        val header = article.getElementsByTag("header").first()
        val tagsElement = header.getElementsByClass("news-item__tags-line").first()
        if (!checkTags(tagsElement)) return null
        val tags = getTagsFromElement(tagsElement)

        val body = article.getElementsByClass("news-item__content").first()
        val bodyContent = getNewsBodyContentFromElement(body) ?: return null
        val bodyImages = getNewsBodyImagesFromElement(body)

        val originalUrl = NewsParserNetworkUtils.getOriginalUrl(resource)

        return ParsedNews(
                title,
                summary,
                date.millis,
                tags,
                bodyContent,
                bodyImages,
                originalUrl
        )
    }

    private fun checkTags(tagsElement: Element): Boolean {
        for (tag in tagsElement.children()) {
            if (tag.tagName() == "span") continue
            if(tag.text().contains("Tribuna", true)) return false
        }
        return true
    }

    private fun getNewsBodyImagesFromElement(body: Element): List<String> {
        val content = mutableListOf<String>()

        loop@ for (p in body.children()) {
            when {
                p.children().size > 0 && p.child(0).tagName() == "img"
                -> content.add(p.child(0).attr("src"))

                else -> continue@loop
            }
        }

        return content
    }

    private fun getNewsBodyContentFromElement(body: Element): List<String>? {
        val totalBlock = body.getElementsByClass("total-block")
        if (totalBlock.size > 0) return null

        val content = mutableListOf<String>()
        val stringBuilder = StringBuilder()

        loop@ for (p in body.children()) {
            val text = when {
                p.tagName() != "p" -> continue@loop

                p.children().size > 0 && p.child(0).tagName() == "img"
                    -> {
                    if (!stringBuilder.isEmpty()){
                        content.add(stringBuilder.toString())
                        stringBuilder.clear()
                    }

                    continue@loop
                }

//                body.children().last() == p && p.child(0).tag().name == "strong"
//                        && p.child(0).children().size > 0
//                        && p.child(0).child(0).tagName() == "a" -> continue@loop

                p.children().size > 0
                    -> readChildElementAndConvertToString(p) ?: continue@loop

                p.children().size == 0 -> p.text()

                else -> continue@loop
            }

            stringBuilder.append(text)
            stringBuilder.append("\n\n")
        }

        if(stringBuilder.isNotEmpty()) {
            stringBuilder.delete(stringBuilder.length - 2, stringBuilder.length)
            content.add(stringBuilder.toString())
        }

        return content
    }

    private fun readChildElementAndConvertToString(p: Element): String? {
        val isTagOnlyChild = p.children().size == 1
               // && p.ownText().isEmpty()

        return when {
            (isTagOnlyChild
                    && p.child(0).tag().name == "strong"
                    && p.child(0).children().size > 0
                    && p.child(0).child(0).tagName() == "a") -> null

            isTagOnlyChild
                    && p.child(0).tag().name == "iframe" -> null

            else -> p.text()
        }

    }

    private fun getTagsFromElement(element: Element): List<ParsedTag> {
        val tagList = mutableListOf<ParsedTag>()
        for (tag in element.children()) {
            if (tag.tagName() == "span") continue
            val tagName = tag.text()
            tagList.add(
                ParsedTag(
                    tagName
                )
            )
        }
        return tagList
    }


}