package com.mobiletask.ui

import TableData
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.LinearLayoutManager
import com.mobiletask.R
import com.mobiletask.adapter.TableAdapter
import com.mobiletask.databinding.ActivityPreviewBinding

class PreviewActivity : AppCompatActivity() {
    private lateinit var binding: ActivityPreviewBinding
    lateinit var properties: ArrayList<TableData>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_preview)
        properties = intent.getParcelableArrayListExtra<TableData>("properties") ?: arrayListOf()
        initView()
        setUpListeners()
    }

    private fun initView() {
        Log.e("ListSize", properties.size.toString())
        binding.rvData.layoutManager = LinearLayoutManager(this)
        binding.rvData.adapter = TableAdapter(properties)
    }

    private fun setUpListeners() {
        binding.btnBack.setOnClickListener({ onBackPressed() })
        binding.btnNext.setOnClickListener({
            val intent = Intent(this, UIActivity::class.java)
            startActivity(intent)
        })
    }

}