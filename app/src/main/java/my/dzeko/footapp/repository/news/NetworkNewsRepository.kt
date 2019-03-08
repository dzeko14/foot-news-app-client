package my.dzeko.footapp.repository.news

import com.google.firebase.database.ChildEventListener
import com.google.firebase.database.FirebaseDatabase
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class NetworkNewsRepository @Inject constructor(
     firebaseDatabase: FirebaseDatabase
) {
    private val firebaseDBReference = firebaseDatabase.reference.child("news")
    private var isFirebaseDBInitialized = false

    fun addNewsListener(listener: ChildEventListener, date: Long) {
        if (!isFirebaseDBInitialized) {
            firebaseDBReference
                .orderByChild("date")
                .startAt(date.toDouble() + 1)
                .addChildEventListener(listener)
        }
    }
}