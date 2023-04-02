package com.geniouscraft.topappandroid.ui.screens.appListScreen

import android.annotation.SuppressLint
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.TravelExplore
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.geniouscraft.topappandroid.ui.theme.White
import com.geniouscraft.topappandroid.ui.viewmodel.AppsViewModel
import com.geniouscraft.topappandroid.R
import com.geniouscraft.topappandroid.ui.theme.Black
import com.togitech.ccp.component.CountryDialog
import com.togitech.ccp.data.utils.getLibCountries

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun MainScreen(viewModel: AppsViewModel = hiltViewModel()) {
    var isOpenDialog by remember { mutableStateOf(false) }
    var isSplashFinished by remember { mutableStateOf(false) }
    SplashScreen {
        isSplashFinished = true
    }
    if (isSplashFinished) {
        Scaffold(
            topBar = {
                TopAppBar(
                    title = {
                        Text(
                            text = stringResource(id = R.string.current_deals_label),
                            color = Black,
                            fontStyle = FontStyle.Italic,
                            fontFamily = FontFamily.Monospace,
                            fontSize = 26.sp
                        )
                    },
                    backgroundColor = White,
                    actions = {
                        IconButton(onClick = {
                            isOpenDialog = true
                        }) {
                            Icon(
                                imageVector = Icons.Default.TravelExplore,
                                tint = Black,
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
}

