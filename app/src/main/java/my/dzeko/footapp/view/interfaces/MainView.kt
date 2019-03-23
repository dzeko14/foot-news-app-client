package my.dzeko.footapp.view.interfaces

interface MainView {
    fun hideBottomNavView()
    fun showBottomNavView()

    fun showActionBar()
    fun hideActionBar()

    fun setBottomNaViewItem(menuItemId: Int)
}