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
    private val binding: ItemSampleBinding
) : RecyclerView.ViewHolder(binding.root) {

    private var item: SampleItem? = null

    init {
        binding.btnButton.setOnClickListener {
            item?.let { safeItem ->
                safeItem.onFavorite(safeItem.text)
            }
        }
    }

    fun onBind(item: SampleItem) {
        this.item = item
        binding.btnButton.text = item.text
        binding.btnButton.setTextColor(if (item.isDarkBg()) Color.WHITE else Color.BLACK)
        binding.btnButton.backgroundTintList = ColorStateList.valueOf(item.bgColor)
        // FIXED 7. use field favorite
        binding.ivFavorite.isVisible = item.isFavorite
    }

    companion object {
        fun create(
            parent: ViewGroup
        ): SampleViewHolder = SampleViewHolder(
            ItemSampleBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        )
    }
}