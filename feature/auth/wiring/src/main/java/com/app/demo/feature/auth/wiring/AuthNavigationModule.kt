package com.app.demo.feature.auth.wiring

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.app.demo.feature.auth.shared.navigation.AuthNavigationProvider
import com.app.demo.feature.auth.shared.navigation.Auth
import com.app.demo.feature.auth.ui.screen.AuthScreen
import com.app.demo.navigation.shared.navigation.AuthNavGraph
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AuthNavigationModule {

    @Provides
    @Singleton
    fun provideAuthNavigationProvider(): AuthNavigationProvider {
        return object : AuthNavigationProvider {
            override fun registerAuthNavGraph(builder: NavGraphBuilder) {
                builder.navigation<AuthNavGraph>(
                    startDestination = Auth,
                ) {
                    composable<Auth> {
                        AuthScreen()
                    }
                }
            }
        }
    }
}