package com.cartelapps.topappandroid.ui.screens.appListScreen

import android.annotation.SuppressLint
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.runtime.Composable
import com.cartelapps.topappandroid.ui.theme.Purple200
import com.cartelapps.topappandroid.ui.theme.primaryOrange

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun MainScreen() {

    Scaffold(
        topBar = { TopAppBar(title = { Text("Top apps") }, backgroundColor = primaryOrange) },
        content = { AppsListScreen() }
    )
}