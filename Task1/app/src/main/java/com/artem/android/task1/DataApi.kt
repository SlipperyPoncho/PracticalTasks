package com.artem.android.task1

import retrofit2.Response
import retrofit2.http.GET

interface DataApi {
    @GET("/categories")
    suspend fun getCategories(): Response<List<Category>>
    @GET("/events")
    suspend fun getEvents(): Response<List<Event>>
}