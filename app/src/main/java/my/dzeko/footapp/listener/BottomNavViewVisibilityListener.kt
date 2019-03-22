package my.dzeko.footapp.listener

import android.os.Bundle
import androidx.navigation.NavController
import androidx.navigation.NavDestination
import my.dzeko.footapp.R

class BottomNavViewVisibilityListener(
    private val showBottomNavView: (Boolean) -> Unit
) : NavController.OnDestinationChangedListener {

    override fun onDestinationChanged(controller: NavController,
                                      destination: NavDestination,
                                      arguments: Bundle?) {
        when(destination.id) {
            R.id.newsListFragment -> showBottomNavView(true)

            R.id.userNewsListFragment -> showBottomNavView(true)

            R.id.searchTagFragment -> showBottomNavView(true)

            else -> showBottomNavView(false)
        }
    }
}