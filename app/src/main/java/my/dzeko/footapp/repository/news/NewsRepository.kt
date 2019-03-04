package my.dzeko.footapp.repository.news

import android.arch.lifecycle.LiveData
import android.arch.paging.PagedList
import com.google.firebase.database.ChildEventListener
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import my.dzeko.footapp.model.entity.News
import my.dzeko.footapp.model.entity.NewsSummary
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class NewsRepository @Inject constructor(
    private val mLocalRepo: LocalNewsRepository,
    private val mNetworkRepo: NetworkNewsRepository
) {
    private val newsListChangesListener = object : NewsListChangesListener(){
        private val ioScope = CoroutineScope(Dispatchers.IO)

        override fun onCancelled(p0: DatabaseError) {
            //TODO
        }

        override fun onChildChanged(p0: DataSnapshot, p1: String?) {
            //TODO
        }

        override fun onChildAdded(dataSnapshot: DataSnapshot, p1: String?) {
            ioScope.launch {
                val news = dataSnapshot.getValue(News::class.java)
                mLocalRepo.save(news)
            }
        }
    }

    suspend fun getNews(): LiveData<PagedList<NewsSummary>> {
        return withContext(Dispatchers.IO) {
            mNetworkRepo.addNewsListener(newsListChangesListener)
             mLocalRepo.getNews()
        }
    }

    private abstract class NewsListChangesListener : ChildEventListener {
        override fun onChildMoved(p0: DataSnapshot, p1: String?) {

        }

        override fun onChildRemoved(p0: DataSnapshot) {
        }
    }
}