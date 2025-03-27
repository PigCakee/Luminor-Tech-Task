package com.app.demo.feature.auth.shared.navigation

import androidx.navigation.NavGraphBuilder

interface AuthNavigationProvider {
    fun registerAuthNavGraph(builder: NavGraphBuilder)
}