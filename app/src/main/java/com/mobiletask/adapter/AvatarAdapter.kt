package com.mobiletask.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.mobiletask.R
import com.mobiletask.databinding.ItemAvatarBinding
import com.squareup.picasso.Picasso

class AvatarAdapter(
    var imgList: ArrayList<String>,
) : RecyclerView.Adapter<AvatarAdapter.MyHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding: ItemAvatarBinding =
            DataBindingUtil.inflate(inflater, R.layout.item_avatar, parent, false)
        return MyHolder(binding)
    }

    override fun onBindViewHolder(holder: MyHolder, position: Int) {
        val model = imgList[position]
        holder.bind(model)
    }


    override fun getItemCount(): Int {
        return imgList.size
    }

    inner class MyHolder(private val binding: ItemAvatarBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(model: String) {
            Picasso
                .get()
                .load(model)
                .into(binding.imgCard);
        }
    }
}
