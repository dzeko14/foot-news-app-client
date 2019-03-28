package my.dzeko.footapp.di.module

import dagger.Module
import dagger.android.ContributesAndroidInjector
import my.dzeko.footapp.view.fragment.TaggedNewsListFragment

@Module
abstract class TagedNewsListFragmentModule {
    @ContributesAndroidInjector
    abstract fun tagedNewsListFragment(): TaggedNewsListFragment
}