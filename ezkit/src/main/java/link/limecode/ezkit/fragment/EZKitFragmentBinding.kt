package link.limecode.ezkit.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.CallSuper
import androidx.fragment.app.Fragment
import androidx.viewbinding.ViewBinding

abstract class EZKitFragmentBinding<T : ViewBinding> : Fragment() {

    private var _viewBinding: T? = null

    protected val viewBinding: T
        get() = _viewBinding ?: error("view binding not ready")

    @CallSuper
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _viewBinding = bind(inflater, container)
        return _viewBinding!!.root
    }

    @CallSuper
    override fun onDestroyView() {
        super.onDestroyView()
        _viewBinding = null
    }

    abstract fun bind(inflater: LayoutInflater, container: ViewGroup?): T
}