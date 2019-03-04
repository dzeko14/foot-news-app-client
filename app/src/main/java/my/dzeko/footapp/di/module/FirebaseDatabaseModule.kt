package my.dzeko.footapp.di.module

import android.content.Context
import com.google.firebase.FirebaseApp
import com.google.firebase.database.FirebaseDatabase
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class FirebaseDatabaseModule {
    @Provides
    @Singleton
    fun providesFirebaseDatabase(context: Context): FirebaseDatabase {
        FirebaseApp.initializeApp(context)
        return FirebaseDatabase.getInstance()
    }
}