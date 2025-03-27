package com.app.demo.navigation.impl

import com.app.demo.navigation.shared.navigation.NavigationCommand
import com.app.demo.navigation.shared.navigation.NavigationManager
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class NavigationManagerImpl @Inject constructor(
    private val coroutineScope: CoroutineScope
) : NavigationManager {

    private val navigationCommandChannel = Channel<NavigationCommand>(Channel.BUFFERED)
    override val navigationCommandFlow = navigationCommandChannel.receiveAsFlow()

    override fun navigate(command: NavigationCommand) {
        coroutineScope.launch {
            navigationCommandChannel.send(command)
        }
    }
}