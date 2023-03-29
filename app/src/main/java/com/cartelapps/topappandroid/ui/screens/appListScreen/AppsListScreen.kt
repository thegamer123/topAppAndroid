package com.cartelapps.topappandroid.ui.screens.appListScreen

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.TabRowDefaults.Divider
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.cartelapps.topappandroid.data.remote.ApiState
import com.cartelapps.topappandroid.model.AppsDataModel
import com.cartelapps.topappandroid.ui.rows.AppItemRow
import com.cartelapps.topappandroid.ui.theme.secondaryGrey
import com.cartelapps.topappandroid.ui.viewmodel.AppsViewModel


@Composable
fun AppsListScreen(
    viewModel: AppsViewModel = hiltViewModel()
) {

    val uiState = remember { viewModel.uiState }

    when (val apiState = uiState.collectAsState().value) {
        is ApiState.Loading -> {
            Box(
                contentAlignment = Alignment.Center,
                modifier = Modifier.fillMaxSize()
            ) {
                CircularProgressIndicator()
            }
        }
        is ApiState.Success -> {
            val dataList = apiState.data as AppsDataModel
            // UI
            Column(
                modifier = Modifier
                    .fillMaxSize(1F)
                    .verticalScroll(rememberScrollState())
            ) {
                dataList.results.forEach { item ->
                    AppItemRow(appData = item)
                    Divider(
                        color = secondaryGrey,
                        thickness = 1.dp
                    )
                }
            }
        }
        else -> {

        }
    }
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    AppsListScreen()
}