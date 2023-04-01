package com.cartelapps.topappandroid.model

import com.google.gson.annotations.SerializedName

data class AppDataResult(
    @SerializedName("title") var title: String? = null,
    @SerializedName("appId") var appId: String? = null,
    @SerializedName("url") var url: String? = null,
    @SerializedName("icon") var icon: String? = null,
    @SerializedName("developer") var developer: Developer? = Developer(),
    @SerializedName("currency") var currency: String? = null,
    @SerializedName("price") var price: String? = null,
    @SerializedName("originalPrice") var originalPrice: String? = null,
    @SerializedName("free") var free: Boolean? = null,
    @SerializedName("summary") var summary: String? = null,
    @SerializedName("scoreText") var scoreText: String? = null,
    @SerializedName("score") var score: Double? = null,
    @SerializedName("playstoreUrl") var playstoreUrl: String? = null,
    @SerializedName("permissions") var permissions: String? = null,
    @SerializedName("similar") var similar: String? = null,
    @SerializedName("reviews") var reviews: String? = null,
    @SerializedName("datasafety") var datasafety: String? = null,
    @SerializedName("categories") var categories: String? = null

)
