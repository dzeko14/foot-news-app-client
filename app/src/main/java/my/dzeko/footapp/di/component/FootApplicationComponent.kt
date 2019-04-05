package my.dzeko.footapp.di.component

import dagger.Component
import dagger.android.AndroidInjector
import dagger.android.support.AndroidSupportInjectionModule
import my.dzeko.footapp.application.FootApplication
import my.dzeko.footapp.di.module.*
import my.dzeko.footapp.worker.ParsingNewsWorker
import javax.inject.Singleton

@Singleton
@Component(
    modules = [
        AndroidSupportInjectionModule::class,
        FootApplicationModule::class,
        SplashScreenFragmentModule::class,
        NewsListFragmentModule::class,
        AppDatabaseModule::class,
        NewsFragmentModule::class,
        TagedNewsListFragmentModule::class,
        MainActivityModule::class,
        UserNewsFragmentModule::class,
        SearchTagFragmentModule::class,
        NewsParserModule::class
    ]
)
interface FootApplicationComponent : AndroidInjector<FootApplication> {
    fun provideParsingNewsWorker(worker: ParsingNewsWorker)

    @Component.Builder
    abstract class Builder : AndroidInjector.Builder<FootApplication>()
}
