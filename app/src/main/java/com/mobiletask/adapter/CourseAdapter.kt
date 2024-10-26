package com.mobiletask.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.mobiletask.R
import com.mobiletask.databinding.ItemCourseBinding

class CourseAdapter(
    var courseList: ArrayList<String>,
) : RecyclerView.Adapter<CourseAdapter.MyHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding: ItemCourseBinding = DataBindingUtil.inflate(inflater, R.layout.item_course, parent, false)
        return MyHolder(binding)
    }

    override fun onBindViewHolder(holder: MyHolder, position: Int) {
        val model = courseList[position]
        holder.bind(model)
    }

    override fun getItemCount(): Int {
        return courseList.size
    }

    inner class MyHolder(private val binding: ItemCourseBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(model: String) {
            binding.tvCourseName.text = model
        }
    }
}
