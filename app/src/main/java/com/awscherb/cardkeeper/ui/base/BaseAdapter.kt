package com.awscherb.cardkeeper.ui.base

import androidx.recyclerview.widget.RecyclerView

import com.awscherb.cardkeeper.data.model.BaseModel
import com.awscherb.cardkeeper.ui.cards.CardsContract
import java.util.*
import kotlin.collections.ArrayList

abstract class BaseAdapter<T> constructor(
        var presenter: CardsContract.Presenter,
        var objects: List<T> = ArrayList()
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() where T : BaseModel {

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        onBindViewHolder(holder, this[position])
    }

    abstract fun onBindViewHolder(holder: RecyclerView.ViewHolder, item: T)

    operator fun get(position: Int): T = objects[position]

    override fun getItemCount() = objects.size

    fun swapObjects(newObjects: List<T>) {
        objects = newObjects
        notifyDataSetChanged()
    }

    fun swap(first: Int, second:Int){
        Collections.swap(objects, first, second)
        notifyItemMoved(first, second)
    }

    fun remove(position: Int){
        (objects as ArrayList).removeAt(position)
        notifyItemRemoved(position)
    }

}
