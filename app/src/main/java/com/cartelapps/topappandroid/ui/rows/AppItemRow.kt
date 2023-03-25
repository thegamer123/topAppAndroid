package com.cartelapps.topappandroid.ui.rows

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import coil.compose.AsyncImage
import com.cartelapps.topappandroid.model.AppDataResult
import com.cartelapps.topappandroid.ui.theme.Black
import com.cartelapps.topappandroid.ui.theme.White


@Composable
fun AppItemRow(
    appData: AppDataResult
) {
    ConstraintLayout(
        modifier = Modifier
            .background(White)
            .padding(10.dp)
            .height(150.dp)
            .fillMaxWidth()
    ) {

        val (asyncImage, column) = createRefs()

        AsyncImage(
            model = appData.icon,
            contentDescription = "App logo Picture",
            modifier = Modifier
                .width(100.dp)
                .height(100.dp)
                .constrainAs(asyncImage) {
                    top.linkTo(parent.top)
                    absoluteLeft.linkTo(parent.absoluteLeft)
                    bottom.linkTo(parent.bottom)
                }
        )

        Column(
            modifier = Modifier
                .width(0.dp)
                .height(0.dp)
                .constrainAs(column) {
                    top.linkTo(parent.top)
                    end.linkTo(parent.end)
                    bottom.linkTo(parent.bottom)
                    absoluteRight.linkTo(parent.absoluteRight)
                },
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Text(
                text = appData.title.orEmpty(),
                color = Black,
                fontSize = 18.sp,
                modifier = Modifier
                    .fillMaxWidth()
                    .wrapContentHeight()
            )
            Text(
                text = appData.developer?.devId.orEmpty(),
                color = Black,
                fontSize = 16.sp,
                modifier = Modifier
                    .fillMaxWidth()
                    .wrapContentHeight()
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview(
    @PreviewParameter(AppItemMock::class)
    data: AppDataResult
) {
    AppItemRow(data)
}
