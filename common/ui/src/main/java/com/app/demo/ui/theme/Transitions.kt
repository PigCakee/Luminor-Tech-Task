package com.app.demo.ui.theme

import androidx.compose.animation.AnimatedContentTransitionScope
import androidx.compose.animation.EnterTransition
import androidx.compose.animation.ExitTransition
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.navigation.NavBackStackEntry

// Vertical slide transitions
val SlideInUpVertically: AnimatedContentTransitionScope<NavBackStackEntry>.() -> EnterTransition? = {
    slideIntoContainer(AnimatedContentTransitionScope.SlideDirection.Up)
}
val SlideInDownVertically: AnimatedContentTransitionScope<NavBackStackEntry>.() -> EnterTransition? = {
    slideIntoContainer(AnimatedContentTransitionScope.SlideDirection.Down)
}
val SlideOutUpVertically: AnimatedContentTransitionScope<NavBackStackEntry>.() -> ExitTransition? = {
    slideOutOfContainer(AnimatedContentTransitionScope.SlideDirection.Up)
}
val SlideOutDownVertically: AnimatedContentTransitionScope<NavBackStackEntry>.() -> ExitTransition? = {
    slideOutOfContainer(AnimatedContentTransitionScope.SlideDirection.Down)
}

// Horizontal slide transitions
val SlideInStartHorizontally: AnimatedContentTransitionScope<NavBackStackEntry>.() -> EnterTransition? = {
    slideIntoContainer(AnimatedContentTransitionScope.SlideDirection.Start)
}
val SlideInEndHorizontally: AnimatedContentTransitionScope<NavBackStackEntry>.() -> EnterTransition? = {
    slideIntoContainer(AnimatedContentTransitionScope.SlideDirection.End)
}
val SlideOutStartHorizontally: AnimatedContentTransitionScope<NavBackStackEntry>.() -> ExitTransition? = {
    slideOutOfContainer(AnimatedContentTransitionScope.SlideDirection.Start)
}
val SlideOutEndHorizontally: AnimatedContentTransitionScope<NavBackStackEntry>.() -> ExitTransition? = {
    slideOutOfContainer(AnimatedContentTransitionScope.SlideDirection.End)
}

// Fade transitions
val FadeIn: AnimatedContentTransitionScope<NavBackStackEntry>.() -> EnterTransition? = {
    fadeIn(tween(200))
}
val FadeOut: AnimatedContentTransitionScope<NavBackStackEntry>.() -> ExitTransition? = {
    fadeOut(tween(200))
}