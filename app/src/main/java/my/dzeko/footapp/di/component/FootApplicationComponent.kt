package my.dzeko.footapp.di.component

import dagger.Component
import dagger.android.AndroidInjector
import dagger.android.support.AndroidSupportInjectionModule
import my.dzeko.footapp.application.FootApplication

@Component(
    modules = [
        AndroidSupportInjectionModule::class
    ]
)
interface FootApplicationComponent : AndroidInjector<FootApplication> {

    @Component.Builder
    abstract class Builder : AndroidInjector.Builder<FootApplication>()
}
