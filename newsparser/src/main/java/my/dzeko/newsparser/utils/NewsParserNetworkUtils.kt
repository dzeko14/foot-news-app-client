package my.dzeko.newsparser.utils

import org.jsoup.Jsoup
import org.jsoup.nodes.Document

internal object NewsParserNetworkUtils {
    private const val BASE_URL = "https://ua.tribuna.com"
    private const val FOOT_NEWS_URL = "/news/football"

    fun getNewsTitleDocument() :Document {
        return Jsoup.connect(BASE_URL + FOOT_NEWS_URL).get()
    }

    fun getNewsConentDocument(resourceUtl :String) :Document {
        //Catch exception
        return Jsoup.connect(BASE_URL + resourceUtl).get()
    }

    fun getOriginalUrl(resource: String): String {
        return BASE_URL + resource
    }


}