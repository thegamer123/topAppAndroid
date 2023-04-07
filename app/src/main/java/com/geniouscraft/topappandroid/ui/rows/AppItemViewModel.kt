package com.geniouscraft.topappandroid.ui.rows

import android.content.Context
import android.graphics.Bitmap
import android.graphics.drawable.BitmapDrawable
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.palette.graphics.Palette
import coil.ImageLoader
import coil.request.CachePolicy
import coil.request.ImageRequest
import coil.request.SuccessResult
import com.geniouscraft.topappandroid.R
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AppItemViewModel @Inject constructor() : ViewModel() {


    private val _uiState: MutableStateFlow<Pair<Int, Bitmap?>> =
        MutableStateFlow(Pair(R.color.white, null))
    val uiState: StateFlow<Pair<Int, Bitmap?>> = _uiState.asStateFlow()


    suspend fun getPictureBrush(context: Context, url: String?) =
        viewModelScope.launch {
            val loader = ImageLoader(context)
            val imageRequest = ImageRequest.Builder(context)
                .data(url)
                .memoryCacheKey(url)
                .diskCacheKey(url)
                .diskCachePolicy(CachePolicy.ENABLED)
                .memoryCachePolicy(CachePolicy.ENABLED)
                .build()

            val result = (loader.execute(imageRequest) as SuccessResult).drawable
            val bitmap = (result as BitmapDrawable).bitmap

            Palette.Builder(bitmap).generate {
                it?.let { palette ->
                    val dominantColor: Int = palette.getDominantColor(
                        ContextCompat.getColor(context, R.color.white)
                    )
                    _uiState.value = Pair(dominantColor, bitmap)

                }
            }

        }
}