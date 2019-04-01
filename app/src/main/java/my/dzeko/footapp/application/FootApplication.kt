package my.dzeko.footapp.application

import dagger.android.AndroidInjector
import dagger.android.support.DaggerApplication
import my.dzeko.footapp.di.component.DaggerFootApplicationComponent
import net.danlew.android.joda.JodaTimeAndroid

class FootApplication : DaggerApplication() {

    override fun onCreate() {
        super.onCreate()
        JodaTimeAndroid.init(this)
    }

    override fun applicationInjector(): AndroidInjector<out DaggerApplication> {
        return DaggerFootApplicationComponent.builder().create(this)
    }
}