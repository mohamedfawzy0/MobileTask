package com.mobiletask.ui.optionsSheet

import android.app.Dialog
import android.content.Context
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.coordinatorlayout.widget.CoordinatorLayout
import androidx.core.view.isVisible
import androidx.fragment.app.FragmentManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.R
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.google.android.material.snackbar.Snackbar
import com.mobiletask.adapter.PropertiesChildAdapter
import com.mobiletask.databinding.OptionsSheetLayoutBinding
import com.mobiletask.model.PropertiesData
import com.mobiletask.ui.MainViewModel
import java.util.Locale

class SheetSearchOptions(
    private val context: Context,
    optionsList: MutableList<PropertiesData.Option>,
    fragmentManager: FragmentManager,
    onSelectOptionsListener: OnSelectOptionListener,
) :
    BottomSheetDialogFragment(), OnChildItemClickListener {
    private lateinit var binding: OptionsSheetLayoutBinding
    private var onSelectOptionsListener: OnSelectOptionListener
    private  var optionsList: MutableList<PropertiesData.Option>
    private val filteredSearchList: MutableList<PropertiesData.Option> = ArrayList()
    private val adapter: PropertiesChildAdapter by lazy {
        PropertiesChildAdapter(
            context,
            optionsList,
            this
        )
    }

    private var finalQuery = ""

    init {
        this.optionsList = optionsList
        this.onSelectOptionsListener = onSelectOptionsListener
        isCancelable = true
        show(fragmentManager, TAG)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = OptionsSheetLayoutBinding.inflate(inflater, container, false)
        return binding.getRoot()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
//        viewModel.getPropertiesChild(optionId)
//        setUpObservers()
        initList()
        initListeners()
    }

    /*private fun setUpObservers() {
        viewModel.propertiesChildLoading.observe(this) {
            binding.progressBar.isVisible = it
        }
        viewModel.errorMessage.observe(this) {
            Snackbar.make(binding.root, it, Snackbar.LENGTH_INDEFINITE).show()
        }
        viewModel.propertiesChildLiveData.observe(this) {
            it?.let {
                binding.llNoOptions.isVisible = it.isEmpty()
                if (it.isNotEmpty()) {
                    searchList.clear()

                    // Add your fixed item at the beginning of the list
                    val otherItem = PropertiesData(id = 0, name = "Other") // Replace with actual data if needed
                    searchList.add(otherItem)

                    // Add the rest of the items
                    searchList.addAll(it)
                    adapter.notifyDataSetChanged()
                }
            }
        }
    }*/



    override fun onStart() {
        super.onStart()

        val bottomSheetDialog = dialog as? BottomSheetDialog
        bottomSheetDialog?.let {
            val bottomSheetInternal = it.findViewById<View>(R.id.design_bottom_sheet)
            bottomSheetInternal?.let { bottomSheetView ->
                val layoutParams = bottomSheetView.layoutParams as CoordinatorLayout.LayoutParams

                // Set a custom height (e.g., 90% of the screen height)
                val displayMetrics = resources.displayMetrics
                val screenHeight = displayMetrics.heightPixels
                layoutParams.height = (screenHeight * 0.9).toInt()  // 90% of screen height

                bottomSheetView.layoutParams = layoutParams

                // Set behavior to expanded
                val behavior = BottomSheetBehavior.from(bottomSheetView)
                behavior.state = BottomSheetBehavior.STATE_EXPANDED
            }
        }
    }


    private fun initList() {
        binding.rvOptions.setLayoutManager(LinearLayoutManager(context))
        binding.rvOptions.setAdapter(adapter)
    }


    private fun initListeners() {
        binding.ivClose.setOnClickListener { v -> dismiss() }

        binding.edSearch.addTextChangedListener(object : TextWatcher {
            private val DEBOUNCE_DELAY: Long = 300 // milliseconds
            private val handler = Handler(Looper.getMainLooper())
            private var searchRunnable: Runnable? = null

            override fun beforeTextChanged(charSequence: CharSequence, i: Int, i1: Int, i2: Int) {
            }

            override fun onTextChanged(s: CharSequence, i: Int, i1: Int, i2: Int) {
                if (searchRunnable != null) {
                    handler.removeCallbacks(searchRunnable!!)
                }
            }

            override fun afterTextChanged(s: Editable) {
                var query = s.toString()
                if (s.toString().trim { it <= ' ' }.contains("٠") ||
                    s.toString().trim { it <= ' ' }.contains("١") ||
                    s.toString().trim { it <= ' ' }.contains("٢") ||
                    s.toString().trim { it <= ' ' }.contains("٣") ||
                    s.toString().trim { it <= ' ' }.contains("٤") ||
                    s.toString().trim { it <= ' ' }.contains("٥") ||
                    s.toString().trim { it <= ' ' }.contains("٦") ||
                    s.toString().trim { it <= ' ' }.contains("٧") ||
                    s.toString().trim { it <= ' ' }.contains("٨") ||
                    s.toString().trim { it <= ' ' }.contains("٩")
                ) {
                    binding.edSearch.removeTextChangedListener(this)
                    binding.edSearch.setText(query)
                    binding.edSearch.setSelection(binding.edSearch.text.length)
                    binding.edSearch.addTextChangedListener(this)
                }

                finalQuery = query
                searchRunnable = Runnable {
                    if (finalQuery.isEmpty()) {
                        // Show the main list when query is empty
                        binding.icSearch.setVisibility(View.VISIBLE)
//                        binding.icDelete.setVisibility(View.GONE)
                        binding.rvOptions.setVisibility(View.VISIBLE)
                        binding.llNoOptions.setVisibility(View.GONE)
                        filteredSearchList.clear()
                        filteredSearchList.addAll(optionsList)
                    } else {
                        binding.icSearch.setVisibility(View.GONE)
                        // Filter the list based on the query
                        val filteredList: MutableList<PropertiesData.Option> =
                            ArrayList()
                        for (model in optionsList) {
                            val employeeName: String = model.name.trim()

                            if (employeeName.lowercase(Locale.getDefault())
                                    .contains(finalQuery.lowercase(Locale.getDefault()))
                            ) {
                                filteredList.add(model)
                            }
                        }

                        if (filteredList.isEmpty()) {
                            binding.rvOptions.setVisibility(View.GONE)
                            binding.llNoOptions.setVisibility(View.VISIBLE)
                        } else {
                            binding.rvOptions.setVisibility(View.VISIBLE)
                            binding.llNoOptions.setVisibility(View.GONE)
                            filteredSearchList.clear()
                            filteredSearchList.addAll(filteredList)
                        }
                    }
                    adapter.updateList(filteredSearchList, finalQuery)
                    adapter.notifyDataSetChanged()
                }

                handler.postDelayed(searchRunnable!!, DEBOUNCE_DELAY)
            }
        })
    }


    companion object {
        const val TAG: String = "SheetsTag"
    }

    override fun onItemClickListener(option: PropertiesData.Option?) {
        onSelectOptionsListener.OnSelectOption(option!!)
        dismiss()
    }
}