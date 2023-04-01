package com.cartelapps.topappandroid.ui.screens.appListScreen

import android.annotation.SuppressLint
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.TravelExplore
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.hilt.navigation.compose.hiltViewModel
import com.cartelapps.topappandroid.ui.theme.primaryOrange
import com.cartelapps.topappandroid.ui.viewmodel.AppsViewModel
import com.togitech.ccp.component.CountryDialog
import com.togitech.ccp.data.utils.getLibCountries

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun MainScreen(viewModel: AppsViewModel = hiltViewModel()) {
    var isOpenDialog by remember { mutableStateOf(false) }
    Scaffold(
        topBar = {
            TopAppBar(title = { Text("Top apps") }, backgroundColor = primaryOrange,
                actions = {
                    IconButton(onClick = {
                        isOpenDialog = true
                    }) {
                        Icon(
                            imageVector = Icons.Default.TravelExplore,
                            contentDescription = null
                        )
                    }
                })
        },
        content = {
            AppsListScreen()

            if (isOpenDialog)
                CountryDialog(
                    modifier = Modifier,
                    countryList = getLibCountries,
                    onSelected = { country ->
                        isOpenDialog = false
                        viewModel.getAppsListDiscountGames(country.countryCode)
                    },
                    context = LocalContext.current,
                    dialogStatus = false,
                    onDismissRequest = {
                        isOpenDialog = false
                    }
                )
        }
    )
}
