package link.limecode.ezkit.recyclerview

class HolderDefinition<T> internal constructor() {
    var viewType: Int = -1
    lateinit var predicate: HolderPredicate<T>
    lateinit var generator: HolderGenerator<T>
}