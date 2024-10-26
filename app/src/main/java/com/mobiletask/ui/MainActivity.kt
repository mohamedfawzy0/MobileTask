package com.mobiletask.ui

import TableData
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.TextView
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.snackbar.Snackbar
import com.mobiletask.R
import com.mobiletask.adapter.PropertiesAdapter
import com.mobiletask.databinding.ActivityMainBinding
import com.mobiletask.model.Category
import com.mobiletask.model.PropertiesData
import com.mobiletask.model.PropertiesData.Option
import com.mobiletask.ui.optionsSheet.OnItemClickListener
import com.mobiletask.ui.optionsSheet.OnSelectOptionListener
import com.mobiletask.ui.optionsSheet.SheetSearchOptions
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity(), View.OnClickListener, OnItemClickListener,
    OnSelectOptionListener {
    private lateinit var binding: ActivityMainBinding
    private val viewModel by viewModels<MainViewModel>()
    private var selectedMainCategory: Category? = null
    private var selectedSubCategory: Category.CategoryChildren? = null
    private var mainCategoryList = mutableListOf<Category>()
    private var tabledataList = ArrayList<TableData>()
    private var propertiesList = mutableListOf<PropertiesData>()
    private val adapter: PropertiesAdapter by lazy { PropertiesAdapter(propertiesList, this) }
    private var position: Int? = null
    private var propertyValue: String? = ""
    private var selecedOption: Option? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        viewModel.getCategories()
        initView()
        setUpObservers()
        setUpListeners()
    }


    private fun initMainCategorySpinner(mainCategoryList: MutableList<Category>) {
        // Add a placeholder item to the list
        mainCategoryList.add(0, Category(name = "Select Category"))

        // Create an ArrayAdapter that only shows the 'name' field of CategoryData
        val spinnerAdapter: ArrayAdapter<Category> =
            object : ArrayAdapter<Category>(
                this, android.R.layout.simple_spinner_item, mainCategoryList
            ) {
                override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
                    val view = super.getView(position, convertView, parent)
                    (view as TextView).text = getItem(position)?.name ?: ""
                    return view
                }

                override fun getDropDownView(
                    position: Int,
                    convertView: View?,
                    parent: ViewGroup
                ): View {
                    val view = super.getDropDownView(position, convertView, parent)
                    (view as TextView).text = getItem(position)?.name ?: ""
                    return view
                }
            }

        spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)

        // Set the adapter to the spinner
        binding.mainCategorySpinner.adapter = spinnerAdapter

        // Set the spinner to the placeholder (position 0)
        binding.mainCategorySpinner.setSelection(0)

        // Check if there is a second category and if it has children
        if (mainCategoryList.size > 1) {
            val hasChildren = mainCategoryList[1].children.isEmpty() == true
            binding.subCategoryProgress.isVisible = !hasChildren
            binding.constSubCategory.isVisible = hasChildren
            initSubCategorySpinner(mutableListOf())
        }

        // Handle item selection logic
        binding.mainCategorySpinner.onItemSelectedListener =
            object : AdapterView.OnItemSelectedListener {
                override fun onItemSelected(
                    parent: AdapterView<*>?,
                    view: View?,
                    position: Int,
                    id: Long
                ) {
                    if (position != 0) { // Ignore the placeholder
                        selectedMainCategory = spinnerAdapter.getItem(position)
                        val childrenList =
                            selectedMainCategory?.children?.toMutableList() ?: mutableListOf()

                        // Update visibility based on the selected main category
                        binding.subCategoryProgress.isVisible = childrenList.isEmpty()
                        binding.constSubCategory.isVisible = childrenList.isNotEmpty()

                        // Initialize subcategory spinner with children
                        initSubCategorySpinner(childrenList)
                    } else {
                        selectedMainCategory =
                            null // Nothing selected when the placeholder is chosen
                        initSubCategorySpinner(mutableListOf()) // Clear subcategory when no main category is selected
                    }
                    binding.rvProperties.isVisible = false
                    adapter.notifyDataSetChanged()
                }

                override fun onNothingSelected(parent: AdapterView<*>?) {}
            }
    }


    private fun initSubCategorySpinner(subCategoryList: MutableList<Category.CategoryChildren>) {
        // Add an empty placeholder as the first item
        subCategoryList.add(
            0,
            Category.CategoryChildren(name = "")
        ) // Empty or use "Select Subcategory" as a placeholder

        val spinnerAdapter: ArrayAdapter<Category.CategoryChildren> =
            object : ArrayAdapter<Category.CategoryChildren>(
                this, android.R.layout.simple_spinner_item, subCategoryList
            ) {
                override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
                    val view = super.getView(position, convertView, parent)
                    (view as TextView).text = getItem(position)?.name ?: ""
                    return view
                }

                override fun getDropDownView(
                    position: Int,
                    convertView: View?,
                    parent: ViewGroup
                ): View {
                    val view = super.getDropDownView(position, convertView, parent)
                    (view as TextView).text = getItem(position)?.name ?: ""
                    return view
                }
            }

        spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)

        // Set the adapter to the spinner
        binding.subCategorySpinner.adapter = spinnerAdapter

        // Set the spinner to the placeholder (position 0, which is the empty string or placeholder)
        binding.subCategorySpinner.setSelection(0)

        // Handle item selection logic
        binding.subCategorySpinner.onItemSelectedListener =
            object : AdapterView.OnItemSelectedListener {
                override fun onItemSelected(
                    parent: AdapterView<*>?,
                    view: View?,
                    position: Int,
                    id: Long
                ) {
                    if (position != 0) {
                        selectedSubCategory = spinnerAdapter.getItem(position)
                    } else {
                        selectedSubCategory = null
                    }
                    binding.propertiesProgress.isVisible = false
                    if (subCategoryList.isNotEmpty() && selectedSubCategory != null) {
                        viewModel.getProperties(selectedSubCategory?.id!!)
                    }
                    adapter.notifyDataSetChanged()
                }

                override fun onNothingSelected(parent: AdapterView<*>?) {
                }
            }
    }


    private fun initView() {
        initMainCategorySpinner(mainCategoryList)
        setSupportActionBar(binding.toolBar)
        binding.rvProperties.layoutManager = LinearLayoutManager(this)
        binding.rvProperties.adapter = adapter
    }

    private fun setUpObservers() {
        viewModel.categoriesLoading.observe(this) {
            binding.categoryProgress.isVisible = it
            binding.constMainCategory.isVisible = !it
        }
        viewModel.propertiesLoading.observe(this) {
            binding.propertiesProgress.isVisible = it
            binding.rvProperties.isVisible = !it
        }
        viewModel.errorMessage.observe(this) {
            Snackbar.make(binding.root, it, Snackbar.LENGTH_INDEFINITE)
                .setDuration(3000)
                .show()
            binding.constMainCategory.isVisible = false
            binding.constSubCategory.isVisible = false
            binding.categoryProgress.isVisible = false
            binding.subCategoryProgress.isVisible = false
            binding.propertiesProgress.isVisible = false
            binding.btnRefresh.isVisible = true

        }
        viewModel.categoriesLiveData.observe(this) {
            it?.let {
                binding.constMainCategory.isVisible = it.isNotEmpty()
                binding.constSubCategory.isVisible = it.isNotEmpty()
                if (it.isNotEmpty()) {
                    mainCategoryList.clear()
                    mainCategoryList.addAll(it)
                }
            }
        }
        viewModel.propertiesLiveData.observe(this) {
            it?.let {
                binding.rvProperties.isVisible = it.isNotEmpty()
                if (it.isNotEmpty()) {
                    propertiesList.clear()
                    propertiesList.addAll(it)

                    tabledataList.clear()

                    for (property in propertiesList) {
                        val selectedOptionName = property.updatedValue
                        tabledataList.add(TableData(property.name, selectedOptionName))
                    }
                    binding.btnSubmet.isVisible = tabledataList.isNotEmpty()
                    adapter.notifyDataSetChanged()
                }
            }
        }
    }

    private fun setUpListeners() {
        binding.btnRefresh.setOnClickListener(this)
        binding.btnSubmet.setOnClickListener(this)
    }

    override fun onClick(v: View?) {
        v?.let {
            when (it.id) {
                R.id.btn_refresh -> {
                    viewModel.getCategories()
                    binding.btnRefresh.isVisible = false
                }

                R.id.btn_submet -> {
                    val intent = Intent(this, PreviewActivity::class.java)
                    intent.putParcelableArrayListExtra("properties", tabledataList)
                    startActivity(intent)
                }
            }
        }
    }

    override fun onItemClickListener(options: ArrayList<Option>?, position: Int) {
        this.position = position

        val otherItem = Option(id = 0, name = "Other")

        if (options != null && !options.contains(otherItem)) {
            options.add(0, otherItem)
        }

        SheetSearchOptions(this, options!!, supportFragmentManager, this)
    }

    override fun OnSelectOption(option: Option) {
        this.selecedOption = option
        position?.let { pos ->
            propertiesList[pos].updatedValue = option.name

            val propertyKey = propertiesList[pos].name
            propertyValue = option.name

            val existingEntryIndex = tabledataList.indexOfFirst { it.key == propertyKey }
            tabledataList[existingEntryIndex].value = option.name
            adapter.notifyDataSetChanged()
        }
    }
}