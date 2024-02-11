package com.alxabr.market_common.base_adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView

abstract class BaseAdapter <Item : Any> : RecyclerView.Adapter<BaseViewHolder<Item>>() {

    protected val items: MutableList<Item> = mutableListOf()

    fun setItems(newItems: List<Item>) {
        val diffResult = DiffUtil.calculateDiff(
            object : DiffUtil.Callback() {
                override fun getOldListSize(): Int =
                    items.size
                override fun getNewListSize(): Int =
                    newItems.size
                override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean =
                    areItemsTheSame(oldItem = items[oldItemPosition], newItem = newItems[newItemPosition])
                override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean =
                    areContentsTheSame(oldItem = items[oldItemPosition], newItem = newItems[newItemPosition])
            }
        )
        this.items.clear()
        this.items.addAll(newItems)
        diffResult.dispatchUpdatesTo(this)
    }

    override fun getItemCount(): Int =
        items.size

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder<Item> =
        instanceViewHolder(
            layoutInflater = LayoutInflater.from(parent.context),
            parent = parent
        )

    override fun onBindViewHolder(holder: BaseViewHolder<Item>, position: Int) {
        items[position].let(holder::bind)
    }

    abstract fun instanceViewHolder(layoutInflater: LayoutInflater, parent: ViewGroup): BaseViewHolder<Item>

    abstract fun areItemsTheSame(oldItem: Item, newItem: Item): Boolean

    abstract fun areContentsTheSame(oldItem: Item, newItem: Item): Boolean
}