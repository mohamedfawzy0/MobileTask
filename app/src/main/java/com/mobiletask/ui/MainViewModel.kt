package com.mobiletask.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mobiletask.model.Category
import com.mobiletask.model.PropertiesData
import com.mobiletask.repository.ApiRepo
import com.mobiletask.repository.ApiResult
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(private val repo: ApiRepo) : ViewModel() {

    private var _categoriesLoadingLivData: MutableLiveData<Boolean> = MutableLiveData(true)
    val categoriesLoading: LiveData<Boolean> = _categoriesLoadingLivData

    private var _propertiesLoadingLivData: MutableLiveData<Boolean> = MutableLiveData(true)
    val propertiesLoading: LiveData<Boolean> = _propertiesLoadingLivData

    private var _propertiesChildLoadingLivData: MutableLiveData<Boolean> = MutableLiveData(true)
    val propertiesChildLoading: LiveData<Boolean> = _propertiesChildLoadingLivData

    private val _categoriesLiveData: MutableLiveData<List<Category>> =
        MutableLiveData()
    val categoriesLiveData: LiveData<List<Category>> =
        _categoriesLiveData

    private val _propertiesLiveData: MutableLiveData<List<PropertiesData>> =
        MutableLiveData()
    val propertiesLiveData: LiveData<List<PropertiesData>> =
        _propertiesLiveData

    private val _propertiesChildLiveData: MutableLiveData<List<PropertiesData>> =
        MutableLiveData()
    val propertiesChildLiveData: LiveData<List<PropertiesData>> =
        _propertiesChildLiveData

    private val _errorMessage: MutableLiveData<String> = MutableLiveData()
    val errorMessage: LiveData<String> = _errorMessage

    fun getCategories() {
        viewModelScope.launch {
            repo.getCategories()
                .flowOn(Dispatchers.IO)
                .onEach {
                    when (it) {
                        is ApiResult.Error -> {
                            _categoriesLoadingLivData.value = false
                            _errorMessage.value = it.message
                        }

                        ApiResult.Loading -> {
                            _categoriesLoadingLivData.value = true
                        }

                        is ApiResult.Success -> {
                            _categoriesLoadingLivData.value = false
                            _categoriesLiveData.value = it.data.data.categories
                        }
                    }
                }.collect()
        }
    }

    fun getProperties(subCategoryId: Int?) {
        viewModelScope.launch {
            repo.getProperties(subCategoryId)
                .flowOn(Dispatchers.IO)
                .onEach {
                    when (it) {
                        is ApiResult.Error -> {
                            _propertiesLoadingLivData.value = false
                            _errorMessage.value = it.message
                        }

                        ApiResult.Loading -> {
                            _propertiesLoadingLivData.value = true
                        }

                        is ApiResult.Success -> {
                            _propertiesLoadingLivData.value = false
                            _propertiesLiveData.value = it.data.data
                        }
                    }
                }.collect()
        }
    }

    fun getPropertiesChild(optionId: Int) {
        viewModelScope.launch {
            repo.getPropertiesChild(optionId)
                .flowOn(Dispatchers.IO)
                .onEach {
                    when (it) {
                        is ApiResult.Error -> {
                            _propertiesChildLoadingLivData.value = false
                            _errorMessage.value = it.message
                        }

                        ApiResult.Loading -> {
                            _propertiesChildLoadingLivData.value = true
                        }

                        is ApiResult.Success -> {
                            _propertiesChildLoadingLivData.value = false
                            _propertiesChildLiveData.value = it.data.data
                        }
                    }
                }.collect()
        }
    }
}
