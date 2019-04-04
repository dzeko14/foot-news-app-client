package my.dzeko.newsparser

import org.junit.Test

import org.junit.Assert.*

class NewsParserTest {

    @Test
    fun parseNews() {
        val newsParser = NewsParser()
        val news = newsParser.parseNews()

        for (n in news) {
            println("----$n\n")
        }
    }
}