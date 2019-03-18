package my.dzeko.footapp.di.module

import dagger.Module
import dagger.android.ContributesAndroidInjector
import my.dzeko.footapp.view.fragment.UserNewsFragment

@Module
abstract class UserNewsFragmentModule {
    @ContributesAndroidInjector
    abstract fun userNewsFragment(): UserNewsFragment
}