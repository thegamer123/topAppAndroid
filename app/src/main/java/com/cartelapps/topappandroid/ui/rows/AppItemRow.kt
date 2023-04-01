package com.cartelapps.topappandroid.ui.rows

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalUriHandler
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.core.net.toUri
import coil.compose.AsyncImage
import coil.request.CachePolicy
import coil.request.ImageRequest
import com.cartelapps.topappandroid.model.AppDataResult
import com.cartelapps.topappandroid.ui.theme.Black
import com.cartelapps.topappandroid.ui.theme.White


@Composable
fun AppItemRow(
    appData: AppDataResult
) {
    val uriHandler = LocalUriHandler.current

    BoxWithConstraints {
        ConstraintLayout(
            modifier = Modifier
                .background(White)
                .wrapContentHeight()
                .fillMaxWidth()
                .clickable {
                    uriHandler.openUri(appData.playstoreUrl.orEmpty())
                }
        ) {

            val context = LocalContext.current
            val imageRequest = ImageRequest.Builder(context)
                .data(appData.icon)
                .memoryCacheKey(appData.icon)
                .diskCacheKey(appData.icon)
                .diskCachePolicy(CachePolicy.ENABLED)
                .memoryCachePolicy(CachePolicy.ENABLED)
                .build()

            val (asyncImage, box) = createRefs()

            AsyncImage(
                model = imageRequest,
                contentDescription = "App logo Picture",
                modifier = Modifier
                    .padding(10.dp)
                    .width(50.dp)
                    .wrapContentHeight()
                    .constrainAs(asyncImage) {
                        top.linkTo(parent.top)
                        absoluteLeft.linkTo(parent.absoluteLeft)
                        bottom.linkTo(parent.bottom)
                    }
            )

            BoxWithConstraints(
                modifier = Modifier
                    .padding(10.dp)
                    .constrainAs(box) {
                        top.linkTo(asyncImage.top)
                        absoluteLeft.linkTo(asyncImage.absoluteRight)
                    }
            ) {
                Column(
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    Box {
                        Text(
                            text = appData.title.orEmpty(),
                            color = Black,
                            fontSize = 16.sp,
                            textAlign = TextAlign.Left,
                            maxLines = 1,
                            overflow = TextOverflow.Ellipsis,
                            modifier = Modifier.padding(start = 4.dp)
                        )
                    }


                    Box {
                        Row {
                            Text(
                                text = appData.developer?.devId.orEmpty(),
                                color = Black,
                                fontSize = 14.sp,
                                textAlign = TextAlign.Left,
                                maxLines = 1,
                                overflow = TextOverflow.Ellipsis,
                                modifier = Modifier.padding(start = 4.dp)
                            )

                        }

                    }
                }
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
