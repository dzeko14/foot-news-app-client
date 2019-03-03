package my.dzeko.footapp.di.module

import android.arch.persistence.room.Room
import android.content.Context
import dagger.Module
import dagger.Provides
import my.dzeko.footapp.database.AppDatabase
import javax.inject.Singleton

private const val DATABASE_NAME = "Foot.db"

@Module
class AppDatabaseModule {
    @Singleton
    @Provides
    fun providesAppDatabase(appContext: Context): AppDatabase {
        return Room
            .databaseBuilder(appContext, AppDatabase::class.java, DATABASE_NAME)
            .build()
    }
}