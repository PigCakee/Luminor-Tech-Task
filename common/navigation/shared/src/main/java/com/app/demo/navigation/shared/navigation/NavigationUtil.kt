package com.app.demo.navigation.shared.navigation

import com.app.demo.ui.model.UiText

fun NavigationManager.forwardTo(destination: Any) = navigate(NavigationCommand.Forward(destination))

fun NavigationManager.replaceBy(destination: Any) = navigate(NavigationCommand.Replace(destination))

fun NavigationManager.back() = navigate(NavigationCommand.Back)

fun NavigationManager.backTo(destination: Any, inclusive: Boolean = false) = navigate(
    NavigationCommand.BackTo(destination, inclusive)
)

fun NavigationManager.showToast(
     title: UiText,
) = navigate(
    NavigationCommand.ShowToast(
        title = title,
    )
)

fun NavigationManager.showGenericErrorToast() = navigate(
    NavigationCommand.ShowToast(
        title = UiText.Id(com.app.demo.ui.R.string.error_generic),
    )
)

fun NavigationManager.navigateToSignInUp() = navigate(
    NavigationCommand.CleanForward(
        destination = AuthNavGraph,
        popDestination = RootNavGraph,
        singleTop = true
    )
)

