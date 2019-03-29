package my.dzeko.footapp.view.activity

import android.os.Bundle
import android.support.design.widget.BottomNavigationView
import android.support.v7.widget.Toolbar
import android.view.View
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupWithNavController
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
    private lateinit var mToolbar: Toolbar

    private val topLevelDestinationIdSet = setOf(
        R.id.newsListFragment,
        R.id.userNewsListFragment,
        R.id.splashScreenFragment,
        R.id.searchTagFragment)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        mPresenter.subscribe(this)

        mNavController = findNavController(R.id.main_container)

        //Set up action bar
        val appBarConfiguration = AppBarConfiguration(topLevelDestinationIdSet)
        mToolbar = findViewById(R.id.toolbar)
        mToolbar.setupWithNavController(mNavController, appBarConfiguration)
        //setSupportActionBar(mToolbar)

        //Set up bottom navigation view
        mBottomNavView = findViewById<BottomNavigationView>(R.id.bottom_nav_view)
            .apply {
            selectedItemId = BottomNavigationViewItemsClickListener.FIRST_MENU_ITEM_ID
            setOnNavigationItemSelectedListener(BottomNavigationViewItemsClickListener(mNavController))
        }

  //      hideActionBar()

        mNavController.addOnDestinationChangedListener(mPresenter.backPressedDestinationChangedListener)

        mNavController.addOnDestinationChangedListener(mPresenter.actionBarDestinationChangedListener)

        mNavController.addOnDestinationChangedListener(
            BottomNavViewVisibilityListener(mPresenter::onBottomNavViewVisibility)
        )
    }

    override fun showActionBar() {
        mToolbar.visibility = View.VISIBLE
    }

    override fun hideActionBar() {
        mToolbar.visibility = View.GONE
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
