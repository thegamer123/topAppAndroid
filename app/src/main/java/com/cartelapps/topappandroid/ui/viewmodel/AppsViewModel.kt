package com.cartelapps.topappandroid.ui.viewmodel

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.cartelapps.topappandroid.data.remote.ApiState
import com.cartelapps.topappandroid.data.remote.repository.AppRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AppsViewModel @Inject constructor(
    private var repository: AppRepository,
    private val savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val myAppsList: MutableStateFlow<ApiState> = MutableStateFlow(ApiState.Empty)
    fun getAppsList() = viewModelScope.launch {
        myAppsList.value = ApiState.Loading
        repository.getAppsList()
            .catch { e ->
                myAppsList.value = ApiState.Failure(e)
            }.collect { data ->
                myAppsList.value = ApiState.Success(data)
            }
    }

}
