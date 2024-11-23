package link.limecode.ezkit.activity

import android.content.res.Configuration
import android.os.Bundle
import android.view.LayoutInflater
import androidx.activity.SystemBarStyle
import androidx.activity.enableEdgeToEdge
import androidx.annotation.CallSuper
import androidx.appcompat.app.AppCompatActivity
import androidx.viewbinding.ViewBinding

abstract class EZKitActivityBinding<T : ViewBinding> : AppCompatActivity() {

    private var _viewBinding: T? = null

    protected val viewBinding: T
        get() = _viewBinding ?: error("view binding not ready")

    @CallSuper
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setupEdgeToEdge()
        _viewBinding = bind(layoutInflater)
        setContentView(_viewBinding!!.root)
    }

    @CallSuper
    override fun onDestroy() {
        super.onDestroy()
        _viewBinding = null
    }

    private fun setupEdgeToEdge() {
        val currentNightMode = resources.configuration.uiMode and Configuration.UI_MODE_NIGHT_MASK
        val transparent = getColor(android.R.color.transparent)
        val systemBarStyle = when (currentNightMode) {
            Configuration.UI_MODE_NIGHT_NO -> SystemBarStyle.light(transparent, transparent)
            Configuration.UI_MODE_NIGHT_YES -> SystemBarStyle.dark(transparent)
            else -> error("Illegal State, current mode is $currentNightMode")
        }
        enableEdgeToEdge(
            statusBarStyle = systemBarStyle,
            navigationBarStyle = systemBarStyle,
        )
    }

    abstract fun bind(inflater: LayoutInflater): T
}