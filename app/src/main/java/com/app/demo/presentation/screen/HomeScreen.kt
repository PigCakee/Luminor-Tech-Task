package com.app.demo.presentation.screen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.app.demo.feature.auth.ui.R
import com.app.demo.ui.component.button.DemoButton
import com.app.demo.ui.component.button.DemoButtonColors
import com.app.demo.ui.component.util.rememberLambda
import com.app.demo.ui.theme.DemoTheme

@Composable
fun HomeScreen() {
    val viewModel = hiltViewModel<HomeViewModel>()
    val state = viewModel.state.collectAsStateWithLifecycle()

    HomeScreenContent(
        state = state,
        onIntent = rememberLambda { it -> viewModel.acceptIntent(it) }
    )
}

@Composable
private fun HomeScreenContent(
    state: State<HomeViewModel.UiState>,
    onIntent: (HomeViewModel.UiIntent) -> Unit
) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 16.dp)
    ) {
        Column(
            modifier = Modifier.align(Alignment.Center),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(24.dp)
        ) {
            Text(
                text = stringResource(R.string.welcome_ph, state.value.user?.email.toString()),
                style = DemoTheme.typography.H3XL,
                color = DemoTheme.colors.textPrimary,
                textAlign = TextAlign.Center
            )
            DemoButton(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(40.dp),
                text = stringResource(R.string.log_out),
                onClick = { onIntent(HomeViewModel.UiIntent.SignOut) },
                buttonColors = DemoButtonColors.primary(),
                shape = DemoTheme.shapes.xxl,
            )
        }
    }
}