package com.geniouscraft.topappandroid.ui.screens.appList.ads

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.viewinterop.AndroidView
import com.geniouscraft.topappandroid.BuildConfig
import com.geniouscraft.topappandroid.R
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.AdSize
import com.google.android.gms.ads.AdView

@Composable
fun BannerAdView(
    bannerAdSize: AdSize
) {
    val unitId = if (BuildConfig.DEBUG)
        stringResource(id = R.string.test_ad_id)
    else stringResource(id = R.string.prod_ad_id)

    AndroidView(
        modifier = Modifier.fillMaxWidth(),
        factory = { context ->
            AdView(context).apply {
                setAdSize(bannerAdSize)
                adUnitId = unitId
                loadAd(AdRequest.Builder().build())
            }
        }
    )
}