package my.dzeko.newsparser

import org.joda.time.DateTime
import org.joda.time.DateTimeZone
import org.junit.Test

import org.junit.Assert.*

class NewsParserTest {

    @Test
    fun parseNews() {
        val lastParsedDate = DateTime(DateTimeZone.forID("Europe/Kiev"))
            .withHourOfDay(23)
            .withMinuteOfHour(9)
        val newsParser = NewsParser()
        val news = newsParser.parseNews(lastParsedDate)

        for (n in news) {
            println("----$n\n")
        }
    }
}