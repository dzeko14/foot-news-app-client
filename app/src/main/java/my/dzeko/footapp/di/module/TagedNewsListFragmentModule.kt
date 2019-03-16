package my.dzeko.footapp.di.module

import dagger.Module
import dagger.android.ContributesAndroidInjector
import my.dzeko.footapp.view.fragment.TagedNewsListFragment

@Module
abstract class TagedNewsListFragmentModule {
    @ContributesAndroidInjector
    abstract fun tagedNewsListFragment(): TagedNewsListFragment
}