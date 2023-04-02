package com.geniouscraft.topappandroid.model

import com.google.gson.annotations.SerializedName

data class AppsDataModel(

    @SerializedName("results") var results: ArrayList<AppDataResult> = arrayListOf(),
    @SerializedName("next") var next: String? = null

)