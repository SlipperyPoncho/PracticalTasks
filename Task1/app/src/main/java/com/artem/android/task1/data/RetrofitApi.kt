package com.artem.android.task1.data

import retrofit2.Response
import retrofit2.http.GET

interface RetrofitApi {
    @GET("/categories")
    suspend fun getCategories(): Response<List<Category>>
    @GET("/events")
    suspend fun getEvents(): Response<List<Event>>
}