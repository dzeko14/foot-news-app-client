package my.dzeko.footapp.di.module

import dagger.Module
import dagger.android.ContributesAndroidInjector
import my.dzeko.footapp.view.fragment.SplashScreenFragment

@Module
abstract class SplashScreenFragmentModule {
    @ContributesAndroidInjector
    abstract fun splashScreenFragment(): SplashScreenFragment
}