package link.limecode.ezkit.activity

import android.os.Bundle
import androidx.annotation.CallSuper
import androidx.annotation.NavigationRes
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import link.limecode.ezkit.R
import link.limecode.ezkit.databinding.ActivityNavHostBinding

abstract class EZKitNavHostActivity :
    EZKitActivityBinding<ActivityNavHostBinding>() {

    abstract val param: Param
    protected lateinit var hostNavController: NavController

    @CallSuper
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment
        hostNavController = navHostFragment.navController
        hostNavController.setGraph(param.graphId)
    }

    data class Param(
        @NavigationRes
        val graphId: Int
    )
}