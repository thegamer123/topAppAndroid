package com.geniouscraft.topappandroid.ui.viewmodel

import android.app.Application
import android.content.Context
import androidx.datastore.preferences.core.edit
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import com.geniouscraft.topappandroid.data.remote.ApiState
import com.geniouscraft.topappandroid.data.remote.repository.AppRepository
import com.geniouscraft.topappandroid.ui.screens.main.countryCodeKey
import com.geniouscraft.topappandroid.ui.screens.main.dataStore
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

    companion object {
        private const val SELECTED_MENU_KEY = "SELECTED_MENU_KEY"
    }

    private val _countryCode: MutableStateFlow<String> =
        MutableStateFlow("de")
    val countryCode: StateFlow<String> = _countryCode.asStateFlow()

    val selectedMenu: Int
        get() = savedStateHandle[SELECTED_MENU_KEY] ?: 0

    fun saveSelectedMenu(selectedMenuPosition: Int) {
        savedStateHandle[SELECTED_MENU_KEY] = selectedMenuPosition
    }

    private val _uiState: MutableStateFlow<ApiState> = MutableStateFlow(ApiState.Empty)
    val uiState: StateFlow<ApiState> = _uiState.asStateFlow()

    init {
        getFreeApps(application.baseContext)
    }

    fun getAppsList(countryCode: String = "de") = viewModelScope.launch {
        _countryCode.value = countryCode
        _uiState.value = ApiState.Loading
        repository.getAppsList(countryCode)
            .catch { e ->
                _uiState.value = ApiState.Failure(e)
            }.collect { data ->
                _uiState.value = ApiState.Success(data)
            }
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

    fun loadDataFollowMenu(context: Context){
        when (selectedMenu) {
            0 -> {
                getFreeApps(context)
            }
            1 -> {
                getExclusiveList(context)
            }
            2 -> {
                getAppsListDiscountGames(context)
            }
            else -> {
                getAppsListDiscountApps(context)
            }
        }
    }


}
