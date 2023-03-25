package com.cartelapps.topappandroid.ui.viewmodel

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class AppsViewModel @Inject constructor(
    private val savedStateHandle: SavedStateHandle
) : ViewModel() {
}