package com.cartelapps.topappandroid.data.remote

import com.cartelapps.topappandroid.model.AppsDataModel
import retrofit2.Response
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