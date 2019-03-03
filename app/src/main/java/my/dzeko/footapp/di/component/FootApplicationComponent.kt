package my.dzeko.footapp.di.component

import dagger.Component
import dagger.android.AndroidInjector
import dagger.android.support.AndroidSupportInjectionModule
import my.dzeko.footapp.application.FootApplication
import my.dzeko.footapp.di.module.NewsListFragmentModule
import my.dzeko.footapp.di.module.SplashScreenFragmentModule
import javax.inject.Singleton

@Singleton
@Component(
    modules = [
        AndroidSupportInjectionModule::class,
        SplashScreenFragmentModule::class,
        NewsListFragmentModule::class
    ]
)
interface FootApplicationComponent : AndroidInjector<FootApplication> {

    @Component.Builder
    abstract class Builder : AndroidInjector.Builder<FootApplication>()
}
