package my.dzeko.footapp.manager

import android.content.Context
import android.net.ConnectivityManager
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ConnectionManager @Inject constructor(
    context: Context
) {
    private val mConnectivityManager = context
        .getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager

    val isConnectedToInternet: Boolean
        get() {
            val networkInfo = mConnectivityManager.activeNetworkInfo
            return networkInfo != null && networkInfo.isConnected
        }
}