package com.geniouscraft.topappandroid.ui.viewmodel

import android.app.Activity
import android.app.Application
import android.content.Context
import android.util.Log
import androidx.datastore.preferences.core.edit
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import com.android.billingclient.api.*
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


    private val _countryCode: MutableStateFlow<String> =
        MutableStateFlow("de")
    val countryCode: StateFlow<String> = _countryCode.asStateFlow()

    private val _selectedMenu: MutableStateFlow<Int> = MutableStateFlow(0)
    val selectedMenu: StateFlow<Int> = _selectedMenu.asStateFlow()

    private val _uiState: MutableStateFlow<ApiState> = MutableStateFlow(ApiState.Empty)
    val uiState: StateFlow<ApiState> = _uiState.asStateFlow()

    private val _productsState: MutableStateFlow<List<ProductDetails?>> =
        MutableStateFlow(emptyList())
    val productsState: StateFlow<List<ProductDetails?>> = _productsState.asStateFlow()

    var billingClient: BillingClient? = null

    private var productDetails : List<ProductDetails?>? = emptyList()

    init {
        getAppsListDiscountGames(application.baseContext)
        billingClient = BillingClient.newBuilder(application.baseContext)
            .setListener(purchasesUpdatedListener)
            .enablePendingPurchases()
            .build()
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

    var isPremium: Boolean
        get() = savedStateHandle.getStateFlow("isPremium", false).value
        set(value) {
            savedStateHandle["isPremium"] = value
        }


    private val purchasesUpdatedListener: PurchasesUpdatedListener
        get() = PurchasesUpdatedListener { billingResult, purchases ->
            if (billingResult.responseCode == BillingClient.BillingResponseCode.OK && purchases != null) {
                for (purchase in purchases) {
                    verifySubPurchase(purchase)
                }
            }
        }

    fun establishConnection() {
        billingClient?.startConnection(object : BillingClientStateListener {
            override fun onBillingSetupFinished(billingResult: BillingResult) {
                if (billingResult.responseCode == BillingClient.BillingResponseCode.OK) {
                    // The BillingClient is ready. You can query purchases here.
                    showProducts()
                }
            }

            override fun onBillingServiceDisconnected() {
                // Try to restart the connection on the next request to
                // Google Play by calling the startConnection() method.
                establishConnection()
            }
        })
    }

    private fun showProducts() {
        val productList =
            listOf( //Product 1
//                QueryProductDetailsParams.Product.newBuilder()
//                    .setProductId("one_week_sub")
//                    .setProductType(BillingClient.ProductType.SUBS)
//                    .build(),  //Product 2
                QueryProductDetailsParams.Product.newBuilder()
                    .setProductId("one_month_sub")
                    .setProductType(BillingClient.ProductType.SUBS)
                    .build(),  //Product 3
//                QueryProductDetailsParams.Product.newBuilder()
//                    .setProductId("one_year_sub")
//                    .setProductType(BillingClient.ProductType.SUBS)
//                    .build()
            )
        val params = QueryProductDetailsParams.newBuilder()
            .setProductList(productList)
            .build()
        billingClient?.queryProductDetailsAsync(
            params
        ) { _: BillingResult?,
            resultProductDetails: List<ProductDetails?>? ->
           productDetails = resultProductDetails
        }
    }

    fun showInAppPurchase(activity : Activity){
        val oneMonthPremium : ProductDetails = productDetails?.last() ?: return
        val offerToken : String = oneMonthPremium.subscriptionOfferDetails?.first()?.offerToken ?: return
        val productDetailsParamsList = listOf(
            BillingFlowParams.ProductDetailsParams.newBuilder()
                // retrieve a value for "productDetails" by calling queryProductDetailsAsync()
                .setProductDetails(oneMonthPremium)
                // to get an offer token, call ProductDetails.subscriptionOfferDetails()
                // for a list of offers that are available to the user
                .setOfferToken(offerToken)
                .build()
        )
        val billingFlowParams = BillingFlowParams.newBuilder()
            .setProductDetailsParamsList(productDetailsParamsList)
            .build()

        billingClient?.launchBillingFlow(activity, billingFlowParams)
    }


    fun verifySubPurchase(purchases: Purchase) {
        val acknowledgePurchaseParams = AcknowledgePurchaseParams
            .newBuilder()
            .setPurchaseToken(purchases.purchaseToken)
            .build()
        billingClient?.acknowledgePurchase(
            acknowledgePurchaseParams
        ) { billingResult: BillingResult ->
            if (billingResult.responseCode == BillingClient.BillingResponseCode.OK) {
                //user prefs to set premium
                //Setting premium to 1
                // 1 - premium
                // 0 - no premium
                isPremium = true
            }
        }
        Log.d("MAIN_ACTIVITY", "Purchase Token: " + purchases.purchaseToken)
        Log.d("MAIN_ACTIVITY", "Purchase Time: " + purchases.purchaseTime)
        Log.d("MAIN_ACTIVITY", "Purchase OrderID: " + purchases.orderId)
    }

}
