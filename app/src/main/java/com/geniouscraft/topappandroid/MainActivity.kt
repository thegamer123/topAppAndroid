package com.geniouscraft.topappandroid

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.ui.Modifier
import com.android.billingclient.api.*
import com.geniouscraft.topappandroid.ui.screens.main.MainScreen
import com.geniouscraft.topappandroid.ui.theme.TopAppAndroidTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    private val billingListener: BillingClientStateListener
        get() = object : BillingClientStateListener {
            override fun onBillingServiceDisconnected() {
                billingClient.startConnection(billingListener)
            }

            override fun onBillingSetupFinished(billingResult: BillingResult) {


                if (billingResult.responseCode == BillingClient.BillingResponseCode.OK) {

                    billingClient.queryProductDetailsAsync(queryProductDetailsParams) { billingResult, productDetailsList ->
                        // check billingResult
                        // process returned productDetailsList
                        val productDetailsParamsList = productDetailsList.map { item ->
                            BillingFlowParams.ProductDetailsParams.newBuilder()
                                // retrieve a value for "productDetails" by calling queryProductDetailsAsync()
                                .setProductDetails(item)
                                // to get an offer token, call ProductDetails.subscriptionOfferDetails()
                                // for a list of offers that are available to the user
                                .setOfferToken(
                                    if (item.productType == BillingClient.ProductType.SUBS)
                                        item.subscriptionOfferDetails.toString()
                                    else
                                        item.oneTimePurchaseOfferDetails.toString()
                                )
                                .build()
                        }


                        val billingFlowParams = BillingFlowParams.newBuilder()
                            .setProductDetailsParamsList(productDetailsParamsList)
                            .build()

                        // Launch the billing flow
                        val billingResult =
                            billingClient.launchBillingFlow(this@MainActivity, billingFlowParams)
                    }

                }
            }

        }

    private val purchasesUpdatedListener: PurchasesUpdatedListener
        get() = PurchasesUpdatedListener { billingResult, purchases ->
            // To be implemented in a later section.
        }

    private val billingClient: BillingClient
        get() = BillingClient.newBuilder(this.baseContext)
            .setListener(purchasesUpdatedListener)
            .enablePendingPurchases()
            .build()


    private val queryProductDetailsParams =
        QueryProductDetailsParams.newBuilder()
            .setProductList(
                listOf(
                    QueryProductDetailsParams.Product.newBuilder()
                        .setProductId("product_id_sub")
                        .setProductType(BillingClient.ProductType.SUBS)
                        .build(),
                    QueryProductDetailsParams.Product.newBuilder()
                        .setProductId("product_id_one_time")
                        .setProductType(BillingClient.ProductType.INAPP)
                        .build()
                )
            )
            .build()


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

        billingClient.startConnection(billingListener)
    }
}
