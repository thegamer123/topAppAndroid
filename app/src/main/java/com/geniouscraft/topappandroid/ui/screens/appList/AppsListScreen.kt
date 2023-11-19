package com.geniouscraft.topappandroid.ui.screens.appList

import NativeAdItem
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import com.geniouscraft.topappandroid.data.remote.ApiState
import com.geniouscraft.topappandroid.ui.rows.AppItemRow
import com.geniouscraft.topappandroid.ui.screens.appList.ads.BannerAdView
import com.geniouscraft.topappandroid.ui.viewmodel.AppsViewModel
import com.geniouscraft.topappandroid.utils.model.AppsDataModel
import com.google.android.gms.ads.AdSize


@Composable
fun AppsListScreen(
    viewModel: AppsViewModel = hiltViewModel()
) {

    val uiState = remember { viewModel.uiState }
    val apiState = uiState.collectAsState().value

    if (apiState is ApiState.Loading) {
        Box(
            contentAlignment = Alignment.Center,
            modifier = Modifier.fillMaxSize()
        ) {
            CircularProgressIndicator()
        }
    }

    if (apiState is ApiState.Success) {
        val dataList: AppsDataModel? =
            remember { (apiState as? ApiState.Success)?.data as? AppsDataModel }
        // UI
        Column(
            modifier = Modifier
                .fillMaxSize(1F)
                .verticalScroll(rememberScrollState()),
            horizontalAlignment = Alignment.CenterHorizontally

        ) {
            //BannerAdView(AdSize.BANNER)
            dataList?.results?.forEachIndexed { index, item ->
                AppItemRow(appData = item)
                if (!viewModel.isPremium) {
                    if (index % 2 == 0) {
                        NativeAdItem()
                    }

//                    if (index % 2 == 0) {
//                        BannerAdView(AdSize.MEDIUM_RECTANGLE)
//                    } else {
//                        NativeAdItem()
//                    }
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    AppsListScreen()
}
