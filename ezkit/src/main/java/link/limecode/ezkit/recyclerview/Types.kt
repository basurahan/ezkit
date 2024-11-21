package link.limecode.ezkit.recyclerview

import android.view.ViewGroup

typealias HolderPredicate<T> = (item: T) -> Boolean
typealias HolderGenerator<T> = (ViewGroup) -> BaseHolder<out T>