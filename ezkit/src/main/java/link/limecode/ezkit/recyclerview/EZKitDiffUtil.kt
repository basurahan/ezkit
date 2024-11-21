package link.limecode.ezkit.recyclerview

import androidx.recyclerview.widget.DiffUtil

abstract class EZKitDiffUtil<T> : DiffUtil.Callback() {

    var newList: List<T> = listOf()
    var oldList: List<T> = listOf()

    abstract fun areItemsTheSame(oldItem: T, newItem: T): Boolean

    abstract fun areContentsTheSame(oldItem: T, newItem: T): Boolean

    override fun getOldListSize(): Int {
        return oldList.size
    }

    override fun getNewListSize(): Int {
        return newList.size
    }

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        val newItem = newList[newItemPosition]
        val oldItem = oldList[oldItemPosition]
        return areItemsTheSame(oldItem, newItem)
    }

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        val newItem = newList[newItemPosition]
        val oldItem = oldList[oldItemPosition]
        return areContentsTheSame(oldItem, newItem)
    }

    override fun getChangePayload(oldItemPosition: Int, newItemPosition: Int): Any? {
        val newItem = newList.getOrNull(newItemPosition)
        val oldItem = oldList.getOrNull(oldItemPosition)

        return when {
            (oldItem != null) && (newItem != null) -> getChangePayload(oldItem, newItem)
            else -> null
        }
    }

    open fun getChangePayload(oldItem: T, newItem: T): Any? {
        return null
    }
}