package link.limecode.ezkit.activity

import android.os.Bundle
import android.view.LayoutInflater
import androidx.annotation.CallSuper
import androidx.appcompat.app.AppCompatActivity
import androidx.viewbinding.ViewBinding
import java.lang.IllegalStateException

abstract class EZKitActivityBinding<T : ViewBinding> : AppCompatActivity() {

    private var _viewBinding: T? = null

    protected val viewBinding: T
        get() = _viewBinding ?: throw IllegalStateException("view binding not ready")

    @CallSuper
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _viewBinding = bind(layoutInflater)
        setContentView(_viewBinding!!.root)
    }

    @CallSuper
    override fun onDestroy() {
        super.onDestroy()
        _viewBinding = null
    }

    abstract fun bind(inflater: LayoutInflater): T
}