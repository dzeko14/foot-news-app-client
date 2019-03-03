package my.dzeko.footapp.di.module

import dagger.Module
import dagger.android.ContributesAndroidInjector
import my.dzeko.footapp.view.fragment.NewsListFragment

@Module
abstract class NewsListFragmentModule {
    @ContributesAndroidInjector
    abstract fun newsListFragment(): NewsListFragment
}