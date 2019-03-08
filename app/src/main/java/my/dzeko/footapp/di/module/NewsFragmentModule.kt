package my.dzeko.footapp.di.module

import dagger.Module
import dagger.android.ContributesAndroidInjector
import my.dzeko.footapp.view.fragment.NewsFragment

@Module
abstract class NewsFragmentModule {
    @ContributesAndroidInjector
    abstract fun newsFragment(): NewsFragment
}