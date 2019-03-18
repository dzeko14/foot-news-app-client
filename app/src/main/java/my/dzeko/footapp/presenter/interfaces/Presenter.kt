package my.dzeko.footapp.presenter.interfaces

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job

abstract class Presenter<T> {
    private val job = Job()
    val uiScope = CoroutineScope(Dispatchers.Main + job)

    var view: T? = null
    private set

    open fun subscribe(view: T) {
        this.view = view
    }

    open fun unsubscribe() {
        view = null
        job.cancel()
    }
}