package my.dzeko.newsparser

import my.dzeko.newsparser.entities.ParsedNews
import my.dzeko.newsparser.utils.NewsParserNetworkUtils
import my.dzeko.newsparser.utils.NewsParserUtils

class NewsParser {
    fun parseNews(): List<ParsedNews> {
        val document = NewsParserNetworkUtils.getNewsTitleDocument()
        return NewsParserUtils.parseNews(document);
    }
}