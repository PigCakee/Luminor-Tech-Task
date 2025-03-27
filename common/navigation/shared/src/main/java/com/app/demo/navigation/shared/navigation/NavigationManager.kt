package com.app.demo.navigation.shared.navigation

import kotlinx.coroutines.flow.Flow

interface NavigationManager {

    val navigationCommandFlow: Flow<NavigationCommand>


    fun navigate(command: NavigationCommand)
}