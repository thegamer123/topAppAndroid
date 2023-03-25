package com.cartelapps.topappandroid.data.remote

import com.cartelapps.topappandroid.model.AppsDataModel
import retrofit2.Response
import retrofit2.http.GET

interface ApiService {

    @GET("apps")
    suspend fun getAllApps(): AppsDataModel
}