package my.dzeko.footapp.listener

import android.support.design.widget.BottomNavigationView
import android.view.MenuItem
import androidx.navigation.NavController
import my.dzeko.footapp.R
import my.dzeko.footapp.extension.popUpTo
import java.util.*

class BottomNavigationViewItemsListener(private val navController: NavController)
    : BottomNavigationView.OnNavigationItemSelectedListener{
    var lastClickedItem = R.id.newsListFragment
    private val itemsDeque: Deque<Int> = LinkedList(listOf(lastClickedItem))

    override fun onNavigationItemSelected(menuItem: MenuItem): Boolean {
        val menuItemId = menuItem.itemId
        if (menuItemId == lastClickedItem) return true
        if (itemsDeque.contains(menuItemId)) {
            navController.popBackStack(menuItemId, false)
            itemsDeque.popUpTo(menuItemId)
        } else {
            itemsDeque.push(menuItemId)
            navController.navigate(menuItemId)
        }
        lastClickedItem = menuItemId
        return true
    }
}