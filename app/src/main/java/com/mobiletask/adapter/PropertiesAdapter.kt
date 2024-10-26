package com.mobiletask.adapter

import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.mobiletask.R
import com.mobiletask.databinding.ItemPropertyBinding
import com.mobiletask.model.PropertiesData
import com.mobiletask.ui.optionsSheet.OnEnterValueListener
import com.mobiletask.ui.optionsSheet.OnItemClickListener

class PropertiesAdapter(
    var propertiesList: MutableList<PropertiesData>,
    private val listener: OnItemClickListener,
    private val enterValueListener: OnEnterValueListener
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
//            binding.llOtherr.isVisible = model.isOther

            // Set the EditText's value based on the model's current input
//            binding.etOther.setText(model.otherInput) // Set the current input value

            // Set up the TextWatcher for the EditText
            /*binding.etOther.addTextChangedListener(object : TextWatcher {
                override fun afterTextChanged(s: Editable?) {
                    // Update the model's input
                    model.otherInput = s.toString() // Store the value in the model
                    enterValueListener.onEnterValueListener(s.toString(),position) // Notify listener
                }

                override fun beforeTextChanged(
                    s: CharSequence?,
                    start: Int,
                    count: Int,
                    after: Int
                ) {
                    // No action needed
                }

                override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                    // No action needed
                }
            })*/

            // Set the click listener for the entire row
            binding.root.setOnClickListener {
                listener.onItemClickListener(model.options, position)
            }
        }
    }
}
