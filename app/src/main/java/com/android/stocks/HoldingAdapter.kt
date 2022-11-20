package com.android.stocks

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView

class HoldingAdapter : RecyclerView.Adapter<HoldingAdapter.HoldingsViewHolder>() {

    private var holdingsUiData: List<HoldingsUiData> = emptyList()

    fun addHoldings(holdingsUiData: List<HoldingsUiData>) {
        this.holdingsUiData = holdingsUiData
        notifyDataSetChanged()
    }

    class HoldingsViewHolder(private val binding: HoldingsItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bindData(holdingsUiData: HoldingsUiData) {
            with(binding) {
                data = holdingsUiData
                executePendingBindings()
            }
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HoldingsViewHolder =
        HoldingsViewHolder(
            HoldingsItemBinding.inflate(
                LayoutInflater.from(parent.context), parent, false
            )
        )

    override fun onBindViewHolder(holder: HoldingsViewHolder, position: Int) {
        holder.bindData(holdingsUiData[position])
    }

    override fun getItemCount() = holdingsUiData.size
}
