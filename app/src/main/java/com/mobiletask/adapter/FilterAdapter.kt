package com.mobiletask.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.mobiletask.R
import com.mobiletask.databinding.ItemFilterBinding

class FilterAdapter(
    var titleList: ArrayList<String>,
) : RecyclerView.Adapter<FilterAdapter.MyHolder>() {

    private var selectedPosition: Int = 0

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding: ItemFilterBinding =
            DataBindingUtil.inflate(inflater, R.layout.item_filter, parent, false)
        return MyHolder(binding)
    }

    override fun onBindViewHolder(holder: MyHolder, position: Int) {
        val model = titleList[position]
        holder.bind(model, position == selectedPosition) // Pass selected state
    }

    override fun getItemCount(): Int {
        return titleList.size
    }

    inner class MyHolder(private val binding: ItemFilterBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(model: String, isSelected: Boolean) {
            binding.tvTitle.text = model
            binding.selected = isSelected

            binding.root.setOnClickListener {
                // Update selected position and notify the adapter
                val previousPosition = selectedPosition
                selectedPosition = adapterPosition
                notifyItemChanged(previousPosition) // Notify previous item to refresh
                notifyItemChanged(selectedPosition) // Notify current item to refresh
            }
        }
    }
}
