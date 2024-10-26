package com.mobiletask.repository

import com.mobiletask.di.ApiInterface
import com.mobiletask.model.CategoriesResponse
import com.mobiletask.model.PropertiesData
import com.mobiletask.model.PropertiesResponse
import dagger.hilt.android.scopes.ViewModelScoped
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

@ViewModelScoped
class ApiRepo @Inject constructor(private val service: ApiInterface) {

    suspend fun getCategories(): Flow<ApiResult<CategoriesResponse>> = flow {
        emit(ApiResult.Loading)
        try {
            val response = service.getCategories()
            emit(ApiResult.Success(response))
        } catch (e: IOException) {
            emit(ApiResult.Error(e.localizedMessage.orEmpty()))
        } catch (e: HttpException) {
            emit(ApiResult.Error(e.localizedMessage.orEmpty()))
        } catch (e: Exception) {
            emit(ApiResult.Error(e.localizedMessage.orEmpty()))
        }
    }

    suspend fun getProperties(subCategoryId:Int?): Flow<ApiResult<PropertiesResponse>> = flow {
        emit(ApiResult.Loading)
        try {
            val response = service.getProperties(subCategoryId)
            emit(ApiResult.Success(response))
        } catch (e: IOException) {
            emit(ApiResult.Error(e.localizedMessage.orEmpty()))
        } catch (e: HttpException) {
            emit(ApiResult.Error(e.localizedMessage.orEmpty()))
        } catch (e: Exception) {
            emit(ApiResult.Error(e.localizedMessage.orEmpty()))
        }
    }
    suspend fun getPropertiesChild(optionId:Int): Flow<ApiResult<PropertiesResponse>> = flow {
        emit(ApiResult.Loading)
        try {
            val response = service.getPropertiesChild(optionId)
            emit(ApiResult.Success(response))
        } catch (e: IOException) {
            emit(ApiResult.Error(e.localizedMessage.orEmpty()))
        } catch (e: HttpException) {
            emit(ApiResult.Error(e.localizedMessage.orEmpty()))
        } catch (e: Exception) {
            emit(ApiResult.Error(e.localizedMessage.orEmpty()))
        }
    }

}

sealed class ApiResult<out T> {
    object Loading : ApiResult<Nothing>()
    data class Success<T>(val data: T) : ApiResult<T>()
    data class Error(val message: String) : ApiResult<Nothing>()
}