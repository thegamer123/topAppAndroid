package com.cartelapps.topappandroid.ui.rows

import androidx.compose.ui.tooling.preview.PreviewParameterProvider
import com.cartelapps.topappandroid.model.AppDataResult
import com.cartelapps.topappandroid.model.Developer

class AppItemMock : PreviewParameterProvider<AppDataResult> {

    private val item = AppDataResult(
        title = "google play App",
        icon = "https://play-lh.googleusercontent.com/y3wnXLnPTdDgG67kUNNbLjp_ggJkrAJ_44L79W57HLU04ANbMtoU34G9nDjyLJxnAEk",
        developer = Developer(devId = "test dev 2")
    )

    override val values: Sequence<AppDataResult>
        get() = listOf(item).asSequence()
}