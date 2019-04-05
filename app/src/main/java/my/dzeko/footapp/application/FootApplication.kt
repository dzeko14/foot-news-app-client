package my.dzeko.footapp.application

import dagger.android.AndroidInjector
import dagger.android.support.DaggerApplication
import my.dzeko.footapp.di.component.DaggerFootApplicationComponent
import my.dzeko.footapp.di.component.FootApplicationComponent
import net.danlew.android.joda.JodaTimeAndroid

class FootApplication : DaggerApplication() {

    lateinit var appComponent: FootApplicationComponent

    override fun onCreate() {
        super.onCreate()
        JodaTimeAndroid.init(this)
    }


    override fun applicationInjector(): AndroidInjector<out DaggerApplication> {
        val injector = DaggerFootApplicationComponent.builder().create(this)
        appComponent = injector as FootApplicationComponent
        return injector
    }
}