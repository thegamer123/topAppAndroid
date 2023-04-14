package com.geniouscraft.topappandroid.ui.screens.appList

import android.annotation.SuppressLint
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.text.InlineTextContent
import androidx.compose.foundation.text.appendInlineContent
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.Placeholder
import androidx.compose.ui.text.PlaceholderVerticalAlign
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.geniouscraft.topappandroid.ui.theme.White
import com.geniouscraft.topappandroid.ui.viewmodel.AppsViewModel
import com.geniouscraft.topappandroid.R
import com.geniouscraft.topappandroid.ui.screens.main.SelectionMenu
import com.geniouscraft.topappandroid.ui.theme.Black
import com.geniouscraft.topappandroid.ui.theme.quickSand
import com.geniouscraft.topappandroid.utils.getFlags
import com.togitech.ccp.component.CountryDialog
import com.togitech.ccp.data.utils.getLibCountries

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun MainScreen(viewModel: AppsViewModel = hiltViewModel()) {
    var isOpenDialog by remember { mutableStateOf(false) }
    var currentCountryCode by remember { mutableStateOf("de") }
    var isSplashFinished by remember { mutableStateOf(false) }
    val context = LocalContext.current

    SplashScreen {
        isSplashFinished = true
    }
    if (isSplashFinished) {
        Scaffold(
            topBar = {
                TopAppBar(
                    title = {

                        val myId = "inlineContent"
                        val text = buildAnnotatedString {
                            append(stringResource(id = R.string.current_deals_label).plus(" "))
                            // Append a placeholder string "[icon]" and attach an annotation "inlineContent" on it.
                            appendInlineContent(myId, "[icon]")
                        }

                        val inlineContent = mapOf(
                            Pair(
                                // This tells the [CoreText] to replace the placeholder string "[icon]" by
                                // the composable given in the [InlineTextContent] object.
                                myId,
                                InlineTextContent(
                                    // Placeholder tells text layout the expected size and vertical alignment of
                                    // children composable.
                                    Placeholder(
                                        width = 25.sp,
                                        height = 25.sp,
                                        placeholderVerticalAlign = PlaceholderVerticalAlign.AboveBaseline
                                    )
                                ) {
                                    // This Icon will fill maximum size, which is specified by the [Placeholder]
                                    // above. Notice the width and height in [Placeholder] are specified in TextUnit,
                                    // and are converted into pixel by text layout.

                                    Image(
                                        painter = painterResource(id = R.drawable.fire),
                                        ""
                                    )
                                }
                            )
                        )


                        Text(
                            text = text,
                            color = Black,
                            fontStyle = FontStyle.Normal,
                            fontFamily = quickSand,
                            fontSize = 26.sp,
                            fontWeight = FontWeight.Bold,
                            inlineContent = inlineContent
                        )
                    },
                    backgroundColor = White,
                    actions = {
                        IconButton(onClick = {
                            isOpenDialog = true
                        }) {
                            Image(
                                modifier = Modifier
                                    .height(25.dp)
                                    .width(25.dp)
                                    .clip(CircleShape),
                                painter = painterResource(id = getFlags(currentCountryCode)),
                                contentDescription = null,
                                contentScale = ContentScale.Crop
                            )
                        }
                    })
            },
            content = {

                Column(
                    modifier = Modifier.padding(top = 10.dp),
                    verticalArrangement = Arrangement.spacedBy(10.dp)
                ) {
                    SelectionMenu()
                    AppsListScreen()
                }
                if (isOpenDialog)
                    CountryDialog(
                        modifier = Modifier,
                        countryList = getLibCountries,
                        onSelected = { country ->
                            isOpenDialog = false
                            currentCountryCode = country.countryCode
                            viewModel.saveCountryCode(
                                context = context,
                                countryCode = currentCountryCode
                            )
                            viewModel.getFreeApps(context = context)
                        },
                        context = LocalContext.current,
                        dialogStatus = false,
                        onDismissRequest = {
                            isOpenDialog = false
                        }
                    )
            }
        )
    }
}

