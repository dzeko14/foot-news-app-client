package my.dzeko.footapp.di.module

import android.content.Context
import dagger.Module
import dagger.Provides
import my.dzeko.footapp.application.FootApplication

@Module
class FootApplicationModule {
    @Provides
    fun providesAppContext(app: FootApplication): Context {
        return app.applicationContext
    }
}