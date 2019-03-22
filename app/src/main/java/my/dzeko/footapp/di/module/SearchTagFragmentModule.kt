package my.dzeko.footapp.di.module

import dagger.Module
import dagger.android.ContributesAndroidInjector
import my.dzeko.footapp.view.fragment.SearchTagFragment

@Module
abstract class SearchTagFragmentModule {
    @ContributesAndroidInjector
    abstract fun searchTagFragment(): SearchTagFragment
}