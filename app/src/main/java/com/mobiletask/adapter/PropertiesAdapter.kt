package com.mobiletask.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.mobiletask.R
import com.mobiletask.databinding.ItemPropertyBinding
import com.mobiletask.model.PropertiesData
import com.mobiletask.ui.optionsSheet.OnItemClickListener

class PropertiesAdapter(
    var propertiesList: MutableList<PropertiesData>,
    private val listener: OnItemClickListener,
) : RecyclerView.Adapter<PropertiesAdapter.MyHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding: ItemPropertyBinding =
            DataBindingUtil.inflate(inflater, R.layout.item_property, parent, false)
        return MyHolder(binding)
    }

    override fun onBindViewHolder(holder: MyHolder, position: Int) {
        val model = propertiesList[position]
        holder.bind(model, position)
    }

    override fun getItemCount(): Int {
        return propertiesList.size
    }

    inner class MyHolder(private val binding: ItemPropertyBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(model: PropertiesData, position: Int) {
            // Bind the data to the UI
            binding.model = model
            // Set the click listener for the entire row
            binding.root.setOnClickListener {
                listener.onItemClickListener(model.options, position)
            }
        }
    }
}
