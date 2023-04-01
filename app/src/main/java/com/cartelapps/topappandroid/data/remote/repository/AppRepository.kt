package com.cartelapps.topappandroid.data.remote.repository

import com.cartelapps.topappandroid.data.Constant.Category.GAMES
import com.cartelapps.topappandroid.data.Constant.Collection.TOP_PAID
import com.cartelapps.topappandroid.data.remote.ApiService
import com.cartelapps.topappandroid.model.AppDataResult
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


    suspend fun getAppsListDiscountApps(countryCode: String): Flow<AppsDataModel> = flow {
        val r = apiService.getAllApps(
            num = 500,
            countryCode = countryCode,
            collection = TOP_PAID
        )
        val filteredData: ArrayList<AppDataResult> =
            ArrayList(r.results.filter { it.originalPrice != null }.take(10))
        emit(
            AppsDataModel(
                results = filteredData,
                next = r.next
            )
        )
    }.flowOn(Dispatchers.IO)


    suspend fun getAppsListDiscountGames(countryCode: String): Flow<AppsDataModel> = flow {
        val r = apiService.getAllApps(
            num = 500,
            countryCode = countryCode,
            collection = TOP_PAID,
            category = GAMES
        )
        val filteredData: ArrayList<AppDataResult> =
            ArrayList(r.results.filter { it.originalPrice != null }.take(10))
        emit(
            AppsDataModel(
                results = filteredData,
                next = r.next
            )
        )
    }.flowOn(Dispatchers.IO)
}