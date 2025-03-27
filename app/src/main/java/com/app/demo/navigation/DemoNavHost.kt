package com.app.demo.navigation

import android.annotation.SuppressLint
import android.util.Log
import android.widget.Toast
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.layout.Box
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import com.app.demo.feature.auth.shared.navigation.AuthNavigationProvider
import com.app.demo.navigation.shared.navigation.NavigationCommand
import com.app.demo.navigation.shared.navigation.NavigationManager
import com.app.demo.navigation.shared.navigation.RootNavGraph

@Composable
@SuppressLint("RestrictedApi")
internal fun DemoNavHost(
    navController: NavHostController,
    navigationManager: NavigationManager,
    authNavigationProvider: AuthNavigationProvider,
    modifier: Modifier = Modifier,
    startDestination: Any = RootNavGraph,
) {
    val context = LocalContext.current

    Box(
        modifier = Modifier.then(modifier),
    ) {
        NavHost(
            navController = navController,
            startDestination = startDestination,
            enterTransition = { fadeIn(tween(150)) },
            exitTransition = { fadeOut(tween(150)) },
            popEnterTransition = { fadeIn(tween(150)) },
            popExitTransition = { fadeOut(tween(150)) },
            builder = {
                authNavigationProvider.registerAuthNavGraph(this)
                rootNavGraph()
            }
        )
    }

    LaunchedEffect(Unit) {
        navigationManager.navigationCommandFlow.collect { command ->
            when (command) {
                is NavigationCommand.Forward -> {
                    navController.navigate(command.destination) {
                        launchSingleTop = command.singleTop
                    }
                }

                is NavigationCommand.CleanForward -> {
                    navController.clearBackStack(command.destination)
                    navController.navigate(command.destination) {
                        command.popDestination?.let { destination ->
                            popUpTo(destination) { inclusive = command.inclusive }
                            launchSingleTop = command.singleTop
                        }
                    }
                }

                is NavigationCommand.Replace -> {
                    navController.popBackStack()
                    navController.navigate(command.destination) {
                        launchSingleTop = command.singleTop
                    }
                }

                is NavigationCommand.Back -> {
                    if (navController.previousBackStackEntry != null) {
                        navController.popBackStack()
                    }
                }

                is NavigationCommand.BackTo -> {
                    navController.popBackStack(
                        route = command.destination,
                        inclusive = command.inclusive,
                    )
                }

                is NavigationCommand.ShowToast -> {
                    Toast.makeText(context, command.title.asString(context), Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    LaunchedEffect(Unit) {
        navController.currentBackStack.collect { backStack ->
            Log.d(
                "Navigation graph",
                backStack.joinToString(" -> ") {
                    it.destination
                        .route
                        ?.split(".")
                        ?.lastOrNull()
                        ?: "Empty"
                }
            )
        }
    }
}
