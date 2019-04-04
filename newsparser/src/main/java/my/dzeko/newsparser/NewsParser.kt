package my.dzeko.newsparser

import my.dzeko.newsparser.entities.ParsedNews
import my.dzeko.newsparser.utils.NewsParserNetworkUtils
import my.dzeko.newsparser.utils.NewsParserUtils
import org.joda.time.DateTime

class NewsParser {
    fun parseNews(lastParsedNewsTime: DateTime = DateTime(0)): List<ParsedNews> {
        val document = NewsParserNetworkUtils.getNewsTitleDocument()
        return NewsParserUtils.parseNews(document, lastParsedNewsTime)
    }
}