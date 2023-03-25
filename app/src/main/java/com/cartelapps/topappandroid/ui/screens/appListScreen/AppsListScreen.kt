package com.cartelapps.topappandroid.ui.screens.appListScreen

import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.sp
import com.cartelapps.topappandroid.ui.theme.Black


@Composable
fun AppsListScreen(name: String) {
    Text(
        text = "Hello $name!",
        color = Black,
        fontSize = 30.sp,
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight()
    )
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    AppsListScreen("Android")
}