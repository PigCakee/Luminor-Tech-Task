package com.app.demo.navigation.shared.navigation

import com.app.demo.ui.model.UiText

sealed interface NavigationCommand {

    data object Back : NavigationCommand

    data class BackTo(
        val destination: Any,
        val inclusive: Boolean = false,
    ) : NavigationCommand

    data class CleanForward(
        val destination: Any,
        val popDestination: Any? = null,
        val inclusive: Boolean = false,
        val singleTop: Boolean = false,
    ) : NavigationCommand

    data class Forward(
        val destination: Any,
        val singleTop: Boolean = false,
    ) : NavigationCommand

    data class Replace(
        val destination: Any,
        val singleTop: Boolean = false,
    ) : NavigationCommand

    data class ShowToast(
        val title: UiText,
    ) : NavigationCommand
}
