package my.dzeko.footapp.repository.news

import androidx.work.*
import my.dzeko.footapp.worker.ParsingNewsWorker
import java.util.concurrent.TimeUnit
import javax.inject.Inject
import javax.inject.Singleton

private const val NEWS_PARSER_WORK = "news_parser_work"

@Singleton
class NetworkNewsRepository @Inject constructor() {
    fun startNewsUpdate() {
        val workManager = WorkManager.getInstance()

        val constraints = Constraints.Builder()
            .setRequiredNetworkType(NetworkType.CONNECTED)
            .build()

        val workRequest = PeriodicWorkRequestBuilder<ParsingNewsWorker>(
            20,
            TimeUnit.MINUTES
        ).setConstraints(constraints)
            .build()

        workManager.enqueueUniquePeriodicWork(NEWS_PARSER_WORK,
            ExistingPeriodicWorkPolicy.REPLACE,
            workRequest)
    }
}