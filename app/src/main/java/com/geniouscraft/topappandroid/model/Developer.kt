package com.geniouscraft.topappandroid.model

import com.google.gson.annotations.SerializedName


data class Developer(

    @SerializedName("devId") var devId: String? = null,
    @SerializedName("url") var url: String? = null

)