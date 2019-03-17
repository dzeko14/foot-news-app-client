package my.dzeko.footapp.view.activity

import android.os.Bundle
import android.support.design.widget.BottomNavigationView
import androidx.navigation.findNavController
import androidx.navigation.ui.setupWithNavController
import dagger.android.support.DaggerAppCompatActivity
import my.dzeko.footapp.R
import my.dzeko.footapp.listener.BottomNavigationViewItemsListener
import my.dzeko.footapp.presenter.MainPresenter
import my.dzeko.footapp.view.interfaces.MainView
import javax.inject.Inject

class MainActivity : DaggerAppCompatActivity(), MainView {

    @Inject lateinit var mPresenter: MainPresenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        mPresenter.subscribe(this)

        val navController = findNavController(R.id.main_container)
        findViewById<BottomNavigationView>(R.id.bottom_nav_view).apply {
            val listener = BottomNavigationViewItemsListener(navController)
            selectedItemId = listener.lastClickedItem
            setOnNavigationItemSelectedListener(listener)
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        mPresenter.unsubscribe()
    }

    override fun hideBottomNavView() {
        TODO("not implemented")
    }

    override fun showBottomNavView() {
        TODO("not implemented")
    }
}
