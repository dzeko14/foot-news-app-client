package my.dzeko.footapp.di.component

import dagger.Component
import dagger.android.AndroidInjector
import dagger.android.support.AndroidSupportInjectionModule
import my.dzeko.footapp.application.FootApplication
import my.dzeko.footapp.di.module.*
import javax.inject.Singleton

@Singleton
@Component(
    modules = [
        AndroidSupportInjectionModule::class,
        FootApplicationModule::class,
        SplashScreenFragmentModule::class,
        NewsListFragmentModule::class,
        AppDatabaseModule::class,
        FirebaseDatabaseModule::class,
        NewsFragmentModule::class,
        TagedNewsListFragmentModule::class
    ]
)
interface FootApplicationComponent : AndroidInjector<FootApplication> {

    @Component.Builder
    abstract class Builder : AndroidInjector.Builder<FootApplication>()
}
