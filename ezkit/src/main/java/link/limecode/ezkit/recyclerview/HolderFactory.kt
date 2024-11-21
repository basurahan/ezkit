package link.limecode.ezkit.recyclerview

import android.view.ViewGroup

interface HolderFactory<T, I> {

    fun buildHolder(parent: ViewGroup, interactor: I): T
}