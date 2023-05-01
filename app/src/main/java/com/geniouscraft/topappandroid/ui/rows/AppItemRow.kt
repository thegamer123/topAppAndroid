package com.geniouscraft.topappandroid.ui.rows

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalUriHandler
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import coil.compose.AsyncImage
import coil.request.CachePolicy
import coil.request.ImageRequest
import com.geniouscraft.topappandroid.R
import com.geniouscraft.topappandroid.utils.model.AppDataResult
import com.geniouscraft.topappandroid.ui.theme.*
import java.util.*


@Composable
fun AppItemRow(
    appData: AppDataResult
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
        backgroundColor = Color.White
    ) {
        ConstraintLayout(
            modifier = Modifier
                .clickable {
                    uriHandler.openUri(appData.playstoreUrl.orEmpty())
                },
        ) {

            val (asyncImageBox, column, dealRatio, hotDeal) = createRefs()

            Box(modifier = Modifier
                .constrainAs(asyncImageBox) {
                    top.linkTo(parent.top)
                    bottom.linkTo(parent.bottom)
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
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

            Card(
                elevation = 5.dp,
                shape = RoundedCornerShape(10.dp),
                modifier = paddingModifier
                    .wrapContentWidth()
                    .wrapContentWidth()
                    .padding(10.dp)
                    .constrainAs(dealRatio) {
                        top.linkTo(parent.top)
                        end.linkTo(parent.end)
                    },
                backgroundColor = White
            ) {
                Text(
                    modifier = Modifier.padding(10.dp, 5.dp, 10.dp, 5.dp),
                    text = if (appData.free == true)
                        stringResource(R.string.free_label) else
                        stringResource(R.string.up_to_label, appData.dealRatioPercentage),
                    color = Black,
                    fontSize = 16.sp,
                    textAlign = TextAlign.Center,
                    maxLines = 2,
                    fontFamily = quickSand,
                    fontWeight = FontWeight.ExtraBold,

                    )
            }


            Image(
                painter = painterResource(id = R.drawable.fire),
                contentDescription = "App is hot deal",
                modifier = paddingModifier
                    .height(40.dp)
                    .width(40.dp)
                    .alpha(if (appData.isHotDeal) 1f else 0f)
                    .constrainAs(hotDeal) {
                        top.linkTo(dealRatio.top)
                        bottom.linkTo(dealRatio.bottom)
                        start.linkTo(parent.start)
                    }
            )


            Column(
                modifier = Modifier
                    .padding(
                        start = 20.dp,
                        top = 10.dp,
                        end = 20.dp,
                        bottom = 10.dp
                    )
                    .wrapContentHeight()
                    .wrapContentWidth()
                    .constrainAs(column) {
                        bottom.linkTo(parent.bottom)
                        absoluteLeft.linkTo(parent.absoluteLeft)
                    },
                verticalArrangement = Arrangement.spacedBy(10.dp)
            ) {

                Card(
                    elevation = 5.dp,
                    shape = RoundedCornerShape(5.dp),
                    modifier = Modifier
                        .wrapContentWidth()
                        .wrapContentWidth(),
                    backgroundColor = White
                ) {
                    Text(
                        modifier = Modifier.padding(10.dp, 5.dp, 10.dp, 5.dp),
                        text = appData.title.orEmpty(),
                        color = Black,
                        fontSize = 16.sp,
                        textAlign = TextAlign.Left,
                        maxLines = 1,
                        fontFamily = FontFamily.Monospace,
                        overflow = TextOverflow.Ellipsis

                    )
                }

                Card(
                    elevation = 5.dp,
                    shape = RoundedCornerShape(5.dp),
                    modifier = Modifier
                        .wrapContentWidth()
                        .wrapContentWidth(),
                    backgroundColor = White
                ) {

                    Text(
                        modifier = Modifier.padding(10.dp, 5.dp, 10.dp, 5.dp),
                        text = appData.developer?.devId.orEmpty(),
                        color = Black,
                        fontSize = 14.sp,
                        textAlign = TextAlign.Left,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis
                    )
                }

                Row {
                    Card(
                        elevation = 5.dp,
                        shape = RoundedCornerShape(5.dp),
                        modifier = Modifier
                            .wrapContentWidth()
                            .wrapContentWidth(),
                        backgroundColor = White
                    ) {
                        Text(
                            modifier = Modifier.padding(10.dp, 5.dp, 10.dp, 5.dp),
                            text = Currency.getInstance(appData.currency).symbol
                                .plus(" ")
                                .plus(appData.originalPrice.orEmpty()),
                            color = Black,
                            fontSize = 13.sp,
                            textAlign = TextAlign.Left,
                            maxLines = 1,
                            overflow = TextOverflow.Ellipsis,
                            style = TextStyle(textDecoration = TextDecoration.LineThrough)
                        )
                    }
                    Card(
                        elevation = 5.dp,
                        shape = RoundedCornerShape(5.dp),
                        modifier = Modifier
                            .padding(start = 10.dp)
                            .wrapContentWidth()
                            .wrapContentWidth(),
                        backgroundColor = Red
                    ) {
                        Text(
                            modifier = Modifier.padding(10.dp, 5.dp, 10.dp, 5.dp),
                            text =
                            stringResource(
                                id = R.string.deal_price_label,
                                Currency.getInstance(appData.currency).symbol
                                    .plus(" ")
                                    .plus(appData.price.orEmpty())
                            ),
                            color = White,
                            fontSize = 13.sp,
                            textAlign = TextAlign.Left,
                            maxLines = 1,
                            overflow = TextOverflow.Ellipsis
                        )
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
