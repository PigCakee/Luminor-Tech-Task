package com.app.demo.presentation

import android.annotation.SuppressLint
import android.graphics.Color
import android.os.Bundle
import androidx.activity.SystemBarStyle
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.animation.Crossfade
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.ui.Modifier
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.compose.rememberNavController
import com.app.demo.feature.auth.shared.navigation.AuthNavigationProvider
import com.app.demo.navigation.DemoNavHost
import com.app.demo.navigation.shared.navigation.AuthNavGraph
import com.app.demo.navigation.shared.navigation.NavigationManager
import com.app.demo.navigation.shared.navigation.RootNavGraph
import com.app.demo.presentation.components.ScreenLoader
import com.app.demo.ui.theme.DemoTheme
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@SuppressLint("RestrictedApi")
@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    @Inject
    lateinit var navigationManager: NavigationManager

    @Inject
    lateinit var authNavigationProvider: AuthNavigationProvider

    override fun onCreate(savedInstanceState: Bundle?) {
        installSplashScreen()
        super.onCreate(savedInstanceState)

        enableEdgeToEdge(
            statusBarStyle = SystemBarStyle.light(
                Color.TRANSPARENT,
                Color.TRANSPARENT
            ),
            navigationBarStyle = SystemBarStyle.light(
                Color.TRANSPARENT,
                Color.WHITE,
            )
        )

        setContent {
            val navController = rememberNavController()
            val viewModel = hiltViewModel<MainViewModel>()
            val state = viewModel.state.collectAsStateWithLifecycle()
            val isLoggedIn = state.value.isLoggedIn

            DemoTheme {
                val modifier = Modifier
                    .background(color = DemoTheme.colors.surface)
                    .fillMaxSize()
                    .systemBarsPadding()
                Crossfade(targetState = isLoggedIn) { isLoggedIn ->
                    if (isLoggedIn != null) {
                        DemoNavHost(
                            navController = navController,
                            navigationManager = navigationManager,
                            modifier = modifier,
                            authNavigationProvider = authNavigationProvider,
                            startDestination = if (isLoggedIn) {
                                RootNavGraph
                            } else {
                                AuthNavGraph
                            }
                        )
                    } else {
                        ScreenLoader()
                    }
                }
            }
        }
    }
}
