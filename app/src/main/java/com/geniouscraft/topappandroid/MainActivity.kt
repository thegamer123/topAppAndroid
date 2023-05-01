package com.geniouscraft.topappandroid

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.ui.Modifier
import com.android.billingclient.api.*
import com.geniouscraft.topappandroid.ui.screens.main.MainScreen
import com.geniouscraft.topappandroid.ui.theme.TopAppAndroidTheme
import com.geniouscraft.topappandroid.ui.viewmodel.AppsViewModel
import com.google.android.gms.ads.MobileAds
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class MainActivity : ComponentActivity() {


    private val viewModel: AppsViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            TopAppAndroidTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier
                        .fillMaxSize()
                        .statusBarsPadding(),
                    color = MaterialTheme.colors.background
                ) {
                    MainScreen()
                }
            }
        }
        MobileAds.initialize(this) {}
        viewModel.establishConnection()
    }


    override fun onResume() {
        super.onResume()

        viewModel.billingClient?.queryPurchasesAsync(
            QueryPurchasesParams.newBuilder().setProductType(BillingClient.ProductType.SUBS).build()
        ) { billingResult: BillingResult, list: List<Purchase> ->
            if (billingResult.responseCode == BillingClient.BillingResponseCode.OK) {
                for (purchase in list) {
                    if (purchase.purchaseState == Purchase.PurchaseState.PURCHASED && !purchase.isAcknowledged) {
                        viewModel.verifySubPurchase(purchase)
                    }
                }
            }
        }
    }


}
