package com.geniouscraft.topappandroid.ui.screens.appListScreen

import android.util.Log
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.runtime.*
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.animateLottieCompositionAsState
import com.airbnb.lottie.compose.rememberLottieComposition

@Composable
fun SplashScreen(callback: () -> Unit) {

    val composition by rememberLottieComposition(
        LottieCompositionSpec.Asset("animations/updates.json")
    )
    var isFinished by remember { mutableStateOf(false) }

    val progress by animateLottieCompositionAsState(
        composition,
        isPlaying = true,
    )

    LaunchedEffect(progress) {
        Log.d("MG-progress", "$progress")
        if (progress >= 1f) {
            isFinished = true
            callback()
        }
    }

    AnimatedVisibility(visible = !isFinished) {
        LottieAnimation(
            composition = composition,
            progress = { progress })
    }

}