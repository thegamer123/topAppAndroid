package com.geniouscraft.topappandroid.data.remote.repository

import com.geniouscraft.topappandroid.data.Constant.Category.APPLICATION
import com.geniouscraft.topappandroid.data.Constant.Category.GAMES
import com.geniouscraft.topappandroid.data.Constant.Collection.TOP_FREE
import com.geniouscraft.topappandroid.data.Constant.Collection.TOP_PAID
import com.geniouscraft.topappandroid.data.remote.ApiService
import com.geniouscraft.topappandroid.utils.model.AppDataResult
import com.geniouscraft.topappandroid.utils.model.AppsDataModel
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


    suspend fun getFreeAppsList(countryCode: String): Flow<AppsDataModel> = flow {
        val r = apiService.getAllApps(
            num = 500,
            countryCode = countryCode,
            collection = TOP_FREE
        )
        val filteredData: ArrayList<AppDataResult> =
            ArrayList(r.results.take(10))
        emit(
            AppsDataModel(
                results = filteredData,
                next = r.next
            )
        )
    }.flowOn(Dispatchers.IO)


    suspend fun getAppsListExclusive(countryCode: String): Flow<AppsDataModel> = flow {

        val apps = apiService.getAllApps(
            num = 500,
            countryCode = countryCode,
            collection = TOP_PAID,
            category = APPLICATION
        )


        val filteredData: ArrayList<AppDataResult> =
            ArrayList(apps.results.filter { it.originalPrice != null && it.isHotDeal }.take(10))
        emit(
            AppsDataModel(
                results = filteredData,
                next = apps.next
            )
        )
    }.flowOn(Dispatchers.IO)

}