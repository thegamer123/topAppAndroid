package com.geniouscraft.topappandroid.data.remote

import com.geniouscraft.topappandroid.model.AppsDataModel
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {

    @GET("apps")
    suspend fun getAllApps(
        @Query("num") num: Int,
        @Query("country") countryCode: String,
        @Query("collection") collection: String = "",
        @Query("category") category: String = "",
    ): AppsDataModel

}