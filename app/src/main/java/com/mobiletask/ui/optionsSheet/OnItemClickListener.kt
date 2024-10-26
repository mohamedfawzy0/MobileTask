package com.mobiletask.ui.optionsSheet

import com.mobiletask.model.PropertiesData
import com.mobiletask.model.PropertiesData.Option

interface OnItemClickListener {
    fun onItemClickListener(options: ArrayList<Option>?,position:Int)
}
