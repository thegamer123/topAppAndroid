package com.cartelapps.topappandroid.ui.rows

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
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

        val (asyncImage, box) = createRefs()

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

        BoxWithConstraints(
            modifier = Modifier
                .constrainAs(box) {
                    top.linkTo(asyncImage.top)
                    absoluteLeft.linkTo(asyncImage.absoluteRight)
                }
        ) {
            Column(
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Text(
                    text = appData.title.orEmpty(),
                    color = Black,
                    fontSize = 18.sp,
                    textAlign = TextAlign.Left,
                    modifier = Modifier.padding(start = 4.dp)
                )
                Text(
                    text = appData.developer?.devId.orEmpty(),
                    color = Black,
                    fontSize = 16.sp,
                    textAlign = TextAlign.Left,
                    modifier = Modifier.padding(start = 4.dp)
                )
            }
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
