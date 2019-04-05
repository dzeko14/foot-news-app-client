package my.dzeko.footapp.di.module

import dagger.Module
import dagger.Provides
import my.dzeko.newsparser.NewsParser
import javax.inject.Singleton

@Module
class NewsParserModule {

    @Singleton
    @Provides
    fun providesNewsParser(): NewsParser {
        return NewsParser()
    }
}