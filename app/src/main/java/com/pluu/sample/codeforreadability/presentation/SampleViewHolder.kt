package com.pluu.sample.codeforreadability.presentation

import android.content.res.ColorStateList
import android.graphics.Color
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.recyclerview.widget.RecyclerView
import com.pluu.sample.codeforreadability.databinding.ItemSampleBinding
import com.pluu.sample.codeforreadability.model.SampleItem

class SampleViewHolder(
    private val binding: ItemSampleBinding,
    private val onFavorite: (String) -> Unit
) : RecyclerView.ViewHolder(binding.root) {

    private var item: SampleItem? = null

    init {
        binding.btnButton.setOnClickListener {
            item?.let { safeItem ->
                onFavorite(safeItem.text)
            }
        }
    }

    fun onBind(item: SampleItem, favoriteText: String) {
        this.item = item
        binding.btnButton.text = item.text
        binding.btnButton.setTextColor(if (item.isDarkBg()) Color.WHITE else Color.BLACK)
        binding.btnButton.backgroundTintList = ColorStateList.valueOf(item.bgColor)
        binding.ivFavorite.isVisible = item.text == favoriteText
    }

    companion object {
        fun create(
            parent: ViewGroup,
            onFavorite: (String) -> Unit
        ): SampleViewHolder = SampleViewHolder(
            ItemSampleBinding.inflate(LayoutInflater.from(parent.context), parent, false),
            onFavorite
        )
    }
}