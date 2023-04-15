package com.geniouscraft.topappandroid.ui.screens.main

import android.annotation.SuppressLint
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.geniouscraft.topappandroid.R
import com.geniouscraft.topappandroid.ui.theme.Black
import com.geniouscraft.topappandroid.ui.theme.quickSand
import com.geniouscraft.topappandroid.ui.theme.secondaryGrey
import com.geniouscraft.topappandroid.ui.viewmodel.AppsViewModel

@SuppressLint("FlowOperatorInvokedInComposition")
@Preview
@Composable
fun SelectionMenu(
    viewModel: AppsViewModel = hiltViewModel()
) {

    var selectedPosition  = viewModel.selectedMenu.collectAsState().value
    val context = LocalContext.current


    val selectedMenuItems: List<SelectionMenuItems> = listOf(
        SelectionMenuItems(
            text = stringResource(id = R.string.free_label),
            position = 0,
            isSelected = selectedPosition == 0
        ),
        SelectionMenuItems(
            text = stringResource(id = R.string.exclusive_label),
            position = 1,
            isSelected = selectedPosition == 1
        ),
        SelectionMenuItems(
            text = stringResource(id = R.string.games_label),
            position = 2,
            isSelected = selectedPosition == 2
        ),
        SelectionMenuItems(
            text = stringResource(id = R.string.apps_label),
            position = 3,
            isSelected = selectedPosition == 3
        )
    )

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight(),
        horizontalArrangement = Arrangement.Center
    ) {

        selectedMenuItems.forEach { item ->
            Text(
                text = item.text,
                style = if (item.isSelected) TextStyle(textDecoration = TextDecoration.Underline) else TextStyle(
                    textDecoration = TextDecoration.None
                ),
                color = if (item.isSelected) Black else secondaryGrey,
                fontSize = if (item.isSelected) 20.sp else 18.sp,
                textAlign = TextAlign.Center,
                fontFamily = quickSand,
                fontWeight = FontWeight.ExtraBold,
                modifier = Modifier
                    .padding(start = 10.dp, end = 10.dp)
                    .clickable {
                        selectedPosition = item.position
                        viewModel.updateMenuPosition(selectedPosition)
                        when (selectedPosition) {
                            0 -> {
                                viewModel.getFreeApps(context)
                            }
                            1 -> {
                                viewModel.getExclusiveList(context)
                            }
                            2 -> {
                                viewModel.getAppsListDiscountGames(context)
                            }
                            else -> {
                                viewModel.getAppsListDiscountApps(context)
                            }
                        }
                    }
            )
        }
    }


}
