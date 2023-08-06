import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.constraintlayout.compose.ConstraintLayout
import com.geniouscraft.topappandroid.BuildConfig
import com.geniouscraft.topappandroid.R
import com.geniouscraft.topappandroid.ui.theme.Black
import com.geniouscraft.topappandroid.ui.theme.Red
import com.geniouscraft.topappandroid.ui.theme.White
import com.google.accompanist.drawablepainter.rememberDrawablePainter
import com.google.android.gms.ads.*
import com.google.android.gms.ads.nativead.NativeAd
import com.google.android.gms.ads.nativead.NativeAdOptions
import com.google.android.gms.ads.nativead.NativeAdView
import java.util.*

@Composable
fun NativeAdItem() {
    val paddingModifier = Modifier.padding(10.dp)
    val context = LocalContext.current

    val nativeAdFlow = remember { mutableStateOf<NativeAd?>(null) }

    val nativeAdOptions = NativeAdOptions.Builder()
        .setVideoOptions(
            VideoOptions.Builder()
                .setStartMuted(true).setClickToExpandRequested(true)
                .build()
        ).setRequestMultipleImages(true)
        .build()

    val unitId =
        if (BuildConfig.DEBUG) context.getString(R.string.test_native_ad_id) else context.getString(
            R.string.native_ad_id
        )


    Card(
        elevation = 20.dp,
        shape = RoundedCornerShape(25.dp),
        modifier = paddingModifier
            .height(350.dp)
            .width(350.dp)
            .padding(start = 10.dp, end = 10.dp),
        backgroundColor = Color.White
    ) {
        AndroidView(
            factory = { context ->
                NativeAdView(context).also { adView ->
                    val adLoader = AdLoader.Builder(context, unitId)
                        .forNativeAd { nativeAd ->
                            nativeAdFlow.value = nativeAd
                            val composeView = ComposeView(context)
                            adView.callToActionView = composeView
                            adView.removeAllViews()
                            adView.addView(composeView)
                            adView.setNativeAd(nativeAd)
                            composeView.setContent {
                                ConstraintLayout {

                                    val (asyncImageBox, column, dealRatio, hotDeal) = createRefs()

                                    Box(modifier = Modifier
                                        .constrainAs(asyncImageBox) {
                                            top.linkTo(parent.top)
                                            bottom.linkTo(parent.bottom)
                                            start.linkTo(parent.start)
                                            end.linkTo(parent.end)
                                        }) {

                                        Image(
                                            painter = rememberDrawablePainter(drawable = nativeAd.mediaContent?.mainImage),
                                            contentDescription = "App logo Picture",
                                            contentScale = ContentScale.Crop,
                                            modifier = Modifier
                                                .fillMaxHeight()
                                                .fillMaxWidth()
                                        )
                                    }

                                    Image(
                                        painter = painterResource(id = R.drawable.ad_badge),
                                        contentDescription = "Ad badge",
                                        modifier = paddingModifier
                                            .wrapContentWidth()
                                            .wrapContentWidth()
                                            .padding(10.dp)
                                            .constrainAs(dealRatio) {
                                                top.linkTo(parent.top)
                                                end.linkTo(parent.end)
                                            }
                                    )


                                    Image(
                                        painter = painterResource(id = R.drawable.fire),
                                        contentDescription = "App is hot deal",
                                        modifier = paddingModifier
                                            .height(40.dp)
                                            .width(40.dp)
                                            .alpha(1f)
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
                                                modifier = Modifier.padding(
                                                    10.dp,
                                                    5.dp,
                                                    10.dp,
                                                    5.dp
                                                ),
                                                text = nativeAd.headline.orEmpty(),
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
                                                modifier = Modifier.padding(
                                                    10.dp,
                                                    5.dp,
                                                    10.dp,
                                                    5.dp
                                                ),
                                                text = nativeAd.advertiser ?: "Advertiser",
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
                                                    modifier = Modifier.padding(
                                                        10.dp,
                                                        5.dp,
                                                        10.dp,
                                                        5.dp
                                                    ),
                                                    text = Currency.getInstance("EUR").symbol
                                                        .plus(" ")
                                                        .plus("10"),
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
                                                    modifier = Modifier.padding(
                                                        10.dp,
                                                        5.dp,
                                                        10.dp,
                                                        5.dp
                                                    ),
                                                    text =
                                                    stringResource(
                                                        id = R.string.deal_price_label,
                                                        Currency.getInstance("EUR").symbol
                                                            .plus(" ")
                                                            .plus("1")
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

                        }.withAdListener(object : AdListener() {
                            override fun onAdFailedToLoad(adError: LoadAdError) {
                            }

                            override fun onAdClicked() {
                                super.onAdClicked()
                            }

                            override fun onAdImpression() {
                                super.onAdImpression()
                            }
                        }).withNativeAdOptions(
                            nativeAdOptions
                        )
                        .build()

                    adLoader.loadAd(AdRequest.Builder().build())

                }
            }, update = { adView ->

            }
        )
    }
}