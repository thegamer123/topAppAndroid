package com.geniouscraft.topappandroid.ui.viewmodel

import android.app.Application
import android.content.Context
import androidx.compose.ui.res.stringResource
import androidx.datastore.preferences.core.edit
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import com.geniouscraft.topappandroid.R
import com.geniouscraft.topappandroid.data.remote.ApiState
import com.geniouscraft.topappandroid.data.remote.repository.AppRepository
import com.geniouscraft.topappandroid.model.AppsDataModel
import com.geniouscraft.topappandroid.ui.screens.main.countryCodeKey
import com.geniouscraft.topappandroid.ui.screens.main.dataStore
import com.google.android.gms.ads.AdListener
import com.google.android.gms.ads.AdLoader
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.LoadAdError
import com.google.android.gms.ads.nativead.NativeAd
import com.google.android.gms.ads.nativead.NativeAdOptions
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AppsViewModel @Inject constructor(
    application: Application,
    private var repository: AppRepository,
    private val savedStateHandle: SavedStateHandle
) : AndroidViewModel(application) {


    private val _countryCode: MutableStateFlow<String> =
        MutableStateFlow("de")
    val countryCode: StateFlow<String> = _countryCode.asStateFlow()

    private val _selectedMenu: MutableStateFlow<Int> = MutableStateFlow(0)
    val selectedMenu: StateFlow<Int> = _selectedMenu.asStateFlow()

    private val _uiState: MutableStateFlow<ApiState> = MutableStateFlow(ApiState.Empty)
    val uiState: StateFlow<ApiState> = _uiState.asStateFlow()

    init {
        getAppsListDiscountGames(application.baseContext)
    }

    fun getAppsListDiscountApps(context: Context) = viewModelScope.launch {

        val countryCode: Flow<String> = context.dataStore.data
            .map { preferences ->
                // No type safety.
                preferences[countryCodeKey] ?: "de"
            }
        countryCode.collectLatest { code ->
            _uiState.value = ApiState.Loading
            repository.getAppsListDiscountApps(code)
                .catch { e ->
                    _uiState.value = ApiState.Failure(e)
                }.collect { data ->
                    _countryCode.value = code
                    _uiState.value = ApiState.Success(data)
                }

        }
    }

    fun updateMenuPosition(menuPosition: Int) {
        _selectedMenu.value = menuPosition
    }

    fun getAppsListDiscountGames(context: Context) = viewModelScope.launch {

        val countryCode: Flow<String> = context.dataStore.data
            .map { preferences ->
                // No type safety.
                preferences[countryCodeKey] ?: "de"
            }

        countryCode.collectLatest { code ->
            _uiState.value = ApiState.Loading
            repository.getAppsListDiscountGames(code)
                .catch { e ->
                    _uiState.value = ApiState.Failure(e)
                }.collect { data ->
                    _countryCode.value = code
                    _uiState.value = ApiState.Success(data)
                }
        }
    }

    fun getExclusiveList(context: Context) = viewModelScope.launch {

        val countryCode: Flow<String> = context.dataStore.data
            .map { preferences ->
                // No type safety.
                preferences[countryCodeKey] ?: "de"
            }

        countryCode.collectLatest { code ->
            _uiState.value = ApiState.Loading
            repository.getAppsListExclusive(code)
                .catch { e ->
                    _uiState.value = ApiState.Failure(e)
                }.collect { data ->
                    _countryCode.value = code
                    _uiState.value = ApiState.Success(data)
                }
        }
    }


    fun saveCountryCode(context: Context, countryCode: String) {
        viewModelScope.launch {
            context.dataStore.edit { settings ->
                settings[countryCodeKey] = countryCode
            }
        }
    }


    fun getFreeApps(context: Context) = viewModelScope.launch {
        val countryCode: Flow<String> = context.dataStore.data
            .map { preferences ->
                // No type safety.
                preferences[countryCodeKey] ?: "de"
            }

        countryCode.collectLatest { code ->
            _uiState.value = ApiState.Loading
            repository.getFreeAppsList(code)
                .catch { e ->
                    _uiState.value = ApiState.Failure(e)
                }.collect { data ->
                    _countryCode.value = code
                    _uiState.value = ApiState.Success(data)
                }
        }

    }



}
