package com.cartelapps.topappandroid.ui.viewmodel

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.cartelapps.topappandroid.data.remote.ApiState
import com.cartelapps.topappandroid.data.remote.repository.AppRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AppsViewModel @Inject constructor(
    private var repository: AppRepository,
    private val savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val _uiState: MutableStateFlow<ApiState> = MutableStateFlow(ApiState.Empty)
    val uiState: StateFlow<ApiState> = _uiState.asStateFlow()

    init {
        getAppsList()
    }

    private fun getAppsList() = viewModelScope.launch {
        _uiState.value = ApiState.Loading
        repository.getAppsList()
            .catch { e ->
                _uiState.value = ApiState.Failure(e)
            }.collect { data ->
                _uiState.value = ApiState.Success(data)
            }
    }

}
