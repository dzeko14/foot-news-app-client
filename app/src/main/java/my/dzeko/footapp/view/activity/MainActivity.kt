package my.dzeko.footapp.view.activity

import android.os.Bundle
import android.support.design.widget.BottomNavigationView
import android.view.View
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import dagger.android.support.DaggerAppCompatActivity
import my.dzeko.footapp.R
import my.dzeko.footapp.listener.BottomNavViewVisibilityListener
import my.dzeko.footapp.listener.BottomNavigationViewItemsClickListener
import my.dzeko.footapp.presenter.MainPresenter
import my.dzeko.footapp.view.interfaces.MainView
import javax.inject.Inject

class MainActivity : DaggerAppCompatActivity(), MainView {

    @Inject lateinit var mPresenter: MainPresenter

    private lateinit var mBottomNavView: BottomNavigationView
    private lateinit var mNavController: NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        mPresenter.subscribe(this)

        mNavController = findNavController(R.id.main_container)

        //Set up navigation bar
        val appBarConfiguration = AppBarConfiguration(setOf(R.id.newsListFragment,
            R.id.userNewsListFragment, R.id.splashScreenFragment))
        setupActionBarWithNavController(mNavController, appBarConfiguration)

        //Set up bottom navigation view
        mBottomNavView = findViewById<BottomNavigationView>(R.id.bottom_nav_view)
            .apply {
            selectedItemId = BottomNavigationViewItemsClickListener.FIRST_MENU_ITEM_ID
            setOnNavigationItemSelectedListener(BottomNavigationViewItemsClickListener(mNavController))
        }

        mNavController.addOnDestinationChangedListener(mPresenter.destinationChangedListener)

        mNavController.addOnDestinationChangedListener(
            BottomNavViewVisibilityListener(mPresenter::onBottomNavViewVisibility)
        )
    }

    override fun onSupportNavigateUp(): Boolean {
        return mNavController.navigateUp() || super.onSupportNavigateUp()
    }

    override fun onBackPressed() {
        mPresenter.onBackPressed()
        super.onBackPressed()
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

    override fun setBottomNaViewItem(menuItemId: Int) {
        mBottomNavView.selectedItemId = menuItemId
    }
}
