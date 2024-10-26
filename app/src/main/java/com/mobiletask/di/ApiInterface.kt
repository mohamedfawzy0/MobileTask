package com.mobiletask.di

import com.mobiletask.model.CategoriesResponse
import com.mobiletask.model.PropertiesData
import com.mobiletask.model.PropertiesResponse
import retrofit2.http.*


interface ApiInterface {

    @GET("get_all_cats")
    suspend fun getCategories(): CategoriesResponse

    @GET("properties")
    suspend fun getProperties(@Query("cat") cat: Int?): PropertiesResponse

    @GET("get-options-child/{id}")
    suspend fun getPropertiesChild(@Path("id") id: Int): PropertiesResponse



}
