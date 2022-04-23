package com.pluu.sample.codeforreadability.presentation

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.pluu.sample.codeforreadability.model.SampleItem

class SampleAdapter(
    private val onDuplicate: (SampleItem) -> Unit,
    private val onFavorite: (String) -> Unit
) : RecyclerView.Adapter<SampleViewHolder>() {

    private var list = mutableListOf<SampleItem>()

    private var favoriteText: String? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SampleViewHolder {
        return SampleViewHolder.create(parent, onFavorite)
    }

    override fun onBindViewHolder(holder: SampleViewHolder, position: Int) {
        holder.onBind(list[position], favoriteText.orEmpty())
    }

    override fun getItemCount(): Int = list.size

    fun addItem(item: SampleItem) {
        val isNoneItem = list.none { it.text == item.text }
        if (isNoneItem) {
            list.add(item)
            list.sortBy { it.text }
            notifyDataSetChanged()
        } else {
            onDuplicate(item)
        }
    }

    fun updateFavorite(text: String) {
        this.favoriteText = text
    }

    fun reset() {
        list.clear()
        notifyDataSetChanged()
    }
}