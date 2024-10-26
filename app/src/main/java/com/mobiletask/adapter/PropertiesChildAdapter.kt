package com.mobiletask.adapter

import android.content.Context
import android.text.Spannable
import android.text.SpannableString
import android.text.style.ForegroundColorSpan
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.mobiletask.R
import com.mobiletask.databinding.ItemPropertyChildBinding
import com.mobiletask.model.PropertiesData
import com.mobiletask.ui.optionsSheet.OnChildItemClickListener
import com.mobiletask.ui.optionsSheet.OnItemClickListener
import java.util.Locale


class PropertiesChildAdapter(
    context: Context,
    var propertiesChildList: MutableList<PropertiesData.Option>,
    private val listener: OnChildItemClickListener
) :
    RecyclerView.Adapter<PropertiesChildAdapter.MyHolder>() {
    private var searchQuery = ""
    private var context: Context = context

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding: ItemPropertyChildBinding =
            DataBindingUtil.inflate(inflater, R.layout.item_property_child, parent, false)
        return MyHolder(binding)
    }

    override fun onBindViewHolder(holder: MyHolder, position: Int) {
        val model = propertiesChildList[position]
        holder.bind(model)
    }

    override fun getItemCount(): Int {
        return propertiesChildList.size
    }

    fun updateList(searchList: MutableList<PropertiesData.Option>, query: String) {
        this.propertiesChildList = searchList
        this.searchQuery = query
        notifyDataSetChanged()
    }

    private fun getHighlightedText(text: String, query: String?): SpannableString {
        val spannableString = SpannableString(text)
        if (query != null && !query.isEmpty()) {
            val startPos =
                text.lowercase(Locale.getDefault()).indexOf(query.lowercase(Locale.getDefault()))
            if (startPos != -1) {
                val endPos = startPos + query.length
                spannableString.setSpan(
                    ForegroundColorSpan(context.getColor(R.color.colorPrimary)),
                    startPos, endPos, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
                )
            }
        }
        return spannableString
    }

    inner class MyHolder(private val binding: ItemPropertyChildBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(model: PropertiesData.Option) {
            binding.model = model
            binding.employeeName.setText(getHighlightedText(model.name, searchQuery))

            binding.root.setOnClickListener {
                listener.onItemClickListener(model)
            }
        }
    }
}