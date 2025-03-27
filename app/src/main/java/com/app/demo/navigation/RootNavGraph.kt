package com.app.demo.navigation

import androidx.annotation.Keep
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import com.app.demo.navigation.shared.navigation.RootNavGraph
import com.app.demo.presentation.screen.HomeScreen
import com.app.demo.ui.theme.SlideInEndHorizontally
import com.app.demo.ui.theme.SlideInStartHorizontally
import com.app.demo.ui.theme.SlideOutEndHorizontally
import com.app.demo.ui.theme.SlideOutStartHorizontally
import kotlinx.serialization.Serializable

@Serializable
@Keep
data object HomeScreen

fun NavGraphBuilder.rootNavGraph() {
    navigation<RootNavGraph>(
        startDestination = HomeScreen
    ) {
        composable<HomeScreen>(
            enterTransition = SlideInStartHorizontally,
            exitTransition = SlideOutStartHorizontally,
            popEnterTransition = SlideInEndHorizontally,
            popExitTransition = SlideOutEndHorizontally
        ) {
            HomeScreen()
        }
    }
}
