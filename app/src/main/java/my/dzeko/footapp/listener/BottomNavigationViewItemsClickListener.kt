package my.dzeko.footapp.listener

import android.support.design.widget.BottomNavigationView
import android.view.MenuItem
import androidx.navigation.NavController
import my.dzeko.footapp.R
import my.dzeko.footapp.extension.popUpTo
import java.util.*

class BottomNavigationViewItemsClickListener(private val navController: NavController)
    : BottomNavigationView.OnNavigationItemSelectedListener{
    private val itemsDeque: Deque<Int> = LinkedList(listOf(FIRST_MENU_ITEM_ID))

    override fun onNavigationItemSelected(menuItem: MenuItem): Boolean {
        val menuItemId = menuItem.itemId
        if (menuItemId == itemsDeque.peek()) return true
        if (itemsDeque.contains(menuItemId)) {
            navController.popBackStack(menuItemId, false)
            itemsDeque.popUpTo(menuItemId)
        } else {
            itemsDeque.push(menuItemId)
            navController.navigate(menuItemId)
        }
        return true
    }

    companion object {
        const val FIRST_MENU_ITEM_ID = R.id.newsListFragment
    }
}