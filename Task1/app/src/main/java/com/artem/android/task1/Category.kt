package com.artem.android.task1

import com.google.gson.annotations.SerializedName

data class Category(
    @SerializedName("title")
    val title: String = "",
    @SerializedName("image")
    val image: String? = null,
    @SerializedName("categoryType")
    val categoryType: CategoryType? = null
)