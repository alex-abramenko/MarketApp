package com.alxabr.market_common.base_adapter

import android.view.View
import androidx.recyclerview.widget.RecyclerView.ViewHolder

/*interface ViewRenderer<in Model : Any> {

    fun render(model: Model)
}

inline fun <Model : Any> diff(block: DiffBuilder<Model>.() -> Unit): ViewRenderer<Model> {
    val builder =
        object : DiffBuilder<Model>(), ViewRenderer<Model> {
            override fun render(model: Model) {
                binders.forEach { it.render(model) }
            }
        }
    builder.block()
    return builder
}

open class DiffBuilder<Model : Any> {

    @PublishedApi
    internal val binders = ArrayList<ViewRenderer<Model>>()

    inline fun <T> diff(
        crossinline get: (Model) -> T,
        crossinline compare: (new: T, old: T) -> Boolean = { a, b -> a == b },
        crossinline set: (T) -> Unit
    ) {
        binders +=
            object : ViewRenderer<Model> {
                private var oldValue: T? = null

                override fun render(model: Model) {
                    val newValue = get(model)
                    val oldValue = oldValue
                    this.oldValue = newValue

                    if ((oldValue == null) || !compare(newValue, oldValue)) {
                        set(newValue)
                    }
                }
            }
    }
}*/

abstract class BaseViewHolder <Item : Any> (rootView: View) : ViewHolder(rootView) {

    protected var item: Item? = null
    /*abstract val renderer: ViewRenderer<Item>*/

    fun bind(item: Item) {
        this.item = item
        render(item)
    }

    abstract fun render(item: Item)

    protected fun requireItem(): Item =
        item ?: throw Throwable("Item not bind.")
}