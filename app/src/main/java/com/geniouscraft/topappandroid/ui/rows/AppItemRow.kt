package com.geniouscraft.topappandroid.ui.rows

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalUriHandler
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.AsyncImage
import coil.request.CachePolicy
import coil.request.ImageRequest
import com.geniouscraft.topappandroid.R
import com.geniouscraft.topappandroid.model.AppDataResult
import com.geniouscraft.topappandroid.ui.theme.Black
import com.geniouscraft.topappandroid.ui.theme.Red
import java.util.*


@Composable
fun AppItemRow(
    appData: AppDataResult,
    viewModel: AppItemViewModel = hiltViewModel()
) {
    val uriHandler = LocalUriHandler.current
    val paddingModifier = Modifier.padding(10.dp)
    val context = LocalContext.current

    Card(
        elevation = 20.dp,
        shape = RoundedCornerShape(25.dp),
        modifier = paddingModifier
            .height(350.dp)
            .width(350.dp)
            .padding(start = 10.dp, end = 10.dp),
        backgroundColor = Color.Transparent
    ) {
        ConstraintLayout(
            modifier = Modifier
                .clickable {
                    uriHandler.openUri(appData.playstoreUrl.orEmpty())
                },
        ) {

            val (asyncImageBox, column) = createRefs()

            Box(modifier = Modifier
                .constrainAs(asyncImageBox) {
                    top.linkTo(parent.top)
                    bottom.linkTo(parent.bottom)
                    absoluteRight.linkTo(parent.absoluteRight)
                    absoluteLeft.linkTo(parent.absoluteLeft)
                }) {

                val imageRequest = ImageRequest.Builder(context)
                    .data(appData.icon)
                    .memoryCacheKey(appData.icon)
                    .diskCacheKey(appData.icon)
                    .diskCachePolicy(CachePolicy.ENABLED)
                    .memoryCachePolicy(CachePolicy.ENABLED)
                    .build()

                AsyncImage(
                    model = imageRequest,
                    contentDescription = "App logo Picture",
                    contentScale = ContentScale.FillBounds,
                    modifier = Modifier
                        .fillMaxHeight()
                        .fillMaxWidth()
                )

            }

            Column(
                modifier = Modifier
                    .padding(
                        start = 20.dp,
                        top = 10.dp,
                        end = 20.dp,
                        bottom = 10.dp
                    )
                    .constrainAs(column) {
                        bottom.linkTo(asyncImageBox.bottom)
                        absoluteLeft.linkTo(parent.absoluteLeft)
                    },
                verticalArrangement = Arrangement.spacedBy(5.dp)
            ) {

                Text(
                    text = appData.title.orEmpty(),
                    color = Black,
                    fontSize = 16.sp,
                    textAlign = TextAlign.Left,
                    maxLines = 1,
                    fontFamily = FontFamily.Monospace,
                    overflow = TextOverflow.Ellipsis

                )

                Text(
                    text = appData.developer?.devId.orEmpty(),
                    color = Black,
                    fontSize = 14.sp,
                    textAlign = TextAlign.Left,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                    modifier = Modifier
                )

                Row(modifier = Modifier.fillMaxHeight()) {
                    Text(
                        text = Currency.getInstance(appData.currency).symbol.plus(
                            appData.originalPrice.orEmpty()
                        ),
                        color = Black,
                        fontSize = 13.sp,
                        textAlign = TextAlign.Left,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis,
                        style = TextStyle(textDecoration = TextDecoration.LineThrough)
                    )

                    Text(
                        text =
                        stringResource(
                            id = R.string.deal_price_label,
                            Currency.getInstance(appData.currency).symbol.plus(
                                appData.price.orEmpty()
                            )
                        ),
                        color = Red,
                        fontSize = 15.sp,
                        textAlign = TextAlign.Left,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis,
                        modifier = Modifier.padding(start = 10.dp)
                    )
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
