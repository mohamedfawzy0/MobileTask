package com.mobiletask.adapter

import TableData
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.mobiletask.R
import com.mobiletask.databinding.ItemTableBinding

class TableAdapter(
    var tableDataList: MutableList<TableData>,
) : RecyclerView.Adapter<TableAdapter.MyHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding: ItemTableBinding =
            DataBindingUtil.inflate(inflater, R.layout.item_table, parent, false)
        return MyHolder(binding)
    }

    override fun onBindViewHolder(holder: MyHolder, position: Int) {
        val model = tableDataList[position]
        holder.bind(model, position == tableDataList.size - 1) // Pass whether this is the last item
    }


    override fun getItemCount(): Int {
        return tableDataList.size
    }

    inner class MyHolder(private val binding: ItemTableBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(model: TableData, isLastItem: Boolean) {
            // Bind the data to the UI
            binding.keyTextView.text = model.key
            binding.valueTextView.text = model.value

            // Show or hide the divider based on whether it's the last item
            binding.dividerView.visibility = if (isLastItem) View.GONE else View.VISIBLE
        }
    }
}
