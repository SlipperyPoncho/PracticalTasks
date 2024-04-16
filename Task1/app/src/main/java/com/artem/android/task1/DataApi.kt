package com.artem.android.task1

import io.reactivex.rxjava3.core.Observable
import retrofit2.http.GET

interface DataApi {
    @GET("/categories")
    fun getCategories(): Observable<List<Category>>
    @GET("/events")
    fun getEvents(): Observable<List<Event>>
}