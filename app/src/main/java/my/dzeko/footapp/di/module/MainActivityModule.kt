package my.dzeko.footapp.di.module

import dagger.Module
import dagger.android.ContributesAndroidInjector
import my.dzeko.footapp.view.activity.MainActivity

@Module
abstract class MainActivityModule {
    @ContributesAndroidInjector
    abstract fun mainActivity(): MainActivity
}