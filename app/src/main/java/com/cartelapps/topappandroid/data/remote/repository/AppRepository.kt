package com.cartelapps.topappandroid.data.remote.repository

import com.cartelapps.topappandroid.data.remote.ApiService
import com.cartelapps.topappandroid.model.AppsDataModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn

class AppRepository(private val apiService: ApiService) {

    suspend fun getAppsList(countryCode: String): Flow<AppsDataModel> = flow {
        val r = apiService.getAllApps(
            num = 50,
            countryCode = countryCode
        )
        emit(r)
    }.flowOn(Dispatchers.IO)
}