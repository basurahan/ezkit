package link.limecode.ezkit.recyclerview

import android.annotation.SuppressLint
import android.view.ViewGroup
import androidx.annotation.CallSuper
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView

class EZKitAdapter<T>(
    val items: MutableList<T> = mutableListOf()
) : RecyclerView.Adapter<BaseHolder<T>>() {

    var diffUtilCallback: EZKitDiffUtil<T>? = null
    private val viewTypes: MutableMap<HolderPredicate<T>, Int> = mutableMapOf()
    private val holderGenerators: MutableMap<Int, HolderGenerator<T>> = mutableMapOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseHolder<T> {
        @Suppress("UNCHECKED_CAST")
        val holder = holderGenerators[viewType]!!(parent) as BaseHolder<T>
        return holder
    }

    override fun getItemCount(): Int {
        return items.size
    }

    @CallSuper
    override fun onBindViewHolder(holder: BaseHolder<T>, position: Int) {
        holder.bind(itemCount, items[position])
    }

    override fun getItemViewType(position: Int): Int {
        try {
            return viewTypes.filterKeys { it(items[position]) }.values.first()
        } catch (e: Throwable) {
            throw RuntimeException(
                "'${javaClass.simpleName}' has no holder for type '${items[position]}'",
                e
            )
        }
    }

    override fun onBindViewHolder(
        holder: BaseHolder<T>,
        position: Int,
        payloads: MutableList<Any>
    ) {
        if (payloads.isNotEmpty()) {
            holder.updateWithPayloads(position, itemCount, items[position], payloads)
        } else {
            super.onBindViewHolder(holder, position, payloads)
        }
    }

    @SuppressLint("NotifyDataSetChanged")
    fun setItems(newItems: List<T>) {
        if (diffUtilCallback != null) {
            diffUtilCallback!!.newList = newItems
            diffUtilCallback!!.oldList = items
            val diffUtilResult = DiffUtil.calculateDiff(diffUtilCallback!!)
            items.clear()
            items += newItems
            diffUtilResult.dispatchUpdatesTo(this)
        } else {
            items.clear()
            items += newItems
            notifyDataSetChanged()
        }
    }

    constructor(
        items: List<T> = listOf(),
        generator: HolderGenerator<T>
    ) : this(items.toMutableList()) {
        oneTypeHolder(generator)
    }

    constructor(
        items: List<T> = listOf(),
        initializer: Initializer<T>
    ) : this(items.toMutableList()) {
        apply { initializer(this) }
    }

    private fun oneTypeHolder(generator: HolderGenerator<T>) {
        holder {
            this.viewType = 0
            this.predicate = { true }
            this.generator = generator
        }
    }

    fun holder(block: HolderDefinition<T>.() -> Unit): EZKitAdapter<T> {
        val hd = HolderDefinition<T>().apply(block)
        viewTypes[hd.predicate] = hd.viewType
        holderGenerators[hd.viewType] = hd.generator
        return this
    }
}