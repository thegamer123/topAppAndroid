package com.geniouscraft.topappandroid.model

import com.google.gson.annotations.SerializedName

data class AppDataResult(
    @SerializedName("title") val title: String? = null,
    @SerializedName("appId") val appId: String? = null,
    @SerializedName("url") val url: String? = null,
    @SerializedName("icon") val icon: String? = null,
    @SerializedName("developer") val developer: Developer? = Developer(),
    @SerializedName("currency") val currency: String? = null,
    @SerializedName("price") val price: String? = null,
    @SerializedName("originalPrice") val originalPrice: String? = null,
    @SerializedName("free") val free: Boolean? = null,
    @SerializedName("summary") val summary: String? = null,
    @SerializedName("scoreText") val scoreText: String? = null,
    @SerializedName("score") val score: Double? = null,
    @SerializedName("playstoreUrl") val playstoreUrl: String? = null,
    @SerializedName("permissions") val permissions: String? = null,
    @SerializedName("similar") val similar: String? = null,
    @SerializedName("reviews") val reviews: String? = null,
    @SerializedName("datasafety") val datasafety: String? = null,
    @SerializedName("categories") val categories: String? = null

) {
    private val dealRatioInt: Int
        get() = 100 - ((price?.toFloat() ?: 1f) / (originalPrice?.toFloat() ?: 1f) * 100).toInt()
    val dealRatioPercentage: String
        get() = "${dealRatioInt}%"

    val isHotDeal: Boolean
        get() = dealRatioInt > 70
}
