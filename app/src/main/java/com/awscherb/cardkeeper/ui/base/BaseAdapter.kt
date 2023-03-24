package com.awscherb.cardkeeper.ui.base

import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.awscherb.cardkeeper.data.model.BaseModel
import com.awscherb.cardkeeper.ui.cards.CardsContract
import java.util.Collections

abstract class BaseAdapter<T, VH: ViewHolder> constructor(
    var presenter: CardsContract.Presenter,
    var objects: List<T> = ArrayList()
): RecyclerView.Adapter<VH>() where T: BaseModel {

    override fun onBindViewHolder(holder: VH, position: Int) {
        onBindViewHolder(holder, this[position])
    }

    abstract fun onBindViewHolder(holder: VH, item: T)

    operator fun get(position: Int): T = objects[position]

    override fun getItemCount() = objects.size

    fun swapObjects(newObjects: List<T>) {
        objects = newObjects
        notifyDataSetChanged()
    }

    fun swap(first: Int, second: Int) {
        Collections.swap(objects, first, second)
        notifyItemMoved(first, second)
    }

    fun remove(position: Int) {
        (objects as ArrayList).removeAt(position)
        notifyItemRemoved(position)
    }
}
