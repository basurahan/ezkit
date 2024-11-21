package link.limecode.ezkit.recyclerview

fun interface Initializer<T> {
    operator fun invoke(adapter: EZKitAdapter<T>)
}