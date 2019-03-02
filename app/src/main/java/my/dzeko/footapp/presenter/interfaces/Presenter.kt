package my.dzeko.footapp.presenter.interfaces

abstract class Presenter<T> {
    var view: T? = null
    private set

    fun subscribe(view: T) {
        this.view = view
    }

    fun unsubscribe() {
        view = null
    }
}