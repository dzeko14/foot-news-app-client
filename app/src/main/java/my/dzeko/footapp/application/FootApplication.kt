package my.dzeko.footapp.application

import dagger.android.AndroidInjector
import dagger.android.support.DaggerApplication
import my.dzeko.footapp.di.component.DaggerFootApplicationComponent

class FootApplication : DaggerApplication() {
    override fun applicationInjector(): AndroidInjector<out DaggerApplication> {
        return DaggerFootApplicationComponent.builder().create(this)
    }
}