package my.dzeko.footapp.view.activity

import android.os.Bundle
import android.support.design.widget.BottomNavigationView
import android.view.View
import androidx.navigation.findNavController
import dagger.android.support.DaggerAppCompatActivity
import my.dzeko.footapp.R
import my.dzeko.footapp.listener.BottomNavViewVisibilityListener
import my.dzeko.footapp.listener.BottomNavigationViewItemsListener
import my.dzeko.footapp.presenter.MainPresenter
import my.dzeko.footapp.view.interfaces.MainView
import javax.inject.Inject

class MainActivity : DaggerAppCompatActivity(), MainView {

    @Inject lateinit var mPresenter: MainPresenter

    private lateinit var mBottomNavView: BottomNavigationView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        mPresenter.subscribe(this)

        val navController = findNavController(R.id.main_container)

        mBottomNavView = findViewById<BottomNavigationView>(R.id.bottom_nav_view)
            .apply {
            val listener = BottomNavigationViewItemsListener(navController)
            selectedItemId = listener.lastClickedItem
            setOnNavigationItemSelectedListener(listener)
        }

        navController.addOnDestinationChangedListener(
            BottomNavViewVisibilityListener(mPresenter::onBottomNavViewVisibility)
        )
    }

    override fun onDestroy() {
        super.onDestroy()
        mPresenter.unsubscribe()
    }

    override fun hideBottomNavView() {
        mBottomNavView.visibility = View.GONE
    }

    override fun showBottomNavView() {
        mBottomNavView.visibility = View.VISIBLE
    }
}
