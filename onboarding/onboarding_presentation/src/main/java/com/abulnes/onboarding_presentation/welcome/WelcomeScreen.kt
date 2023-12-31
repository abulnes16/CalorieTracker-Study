package com.abulnes.onboarding_presentation.welcome

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import com.abulnes.core.R
import com.abulnes.core.navigation.Route
import com.abulnes.core.util.UiEvent
import com.abulnes.core_ui.LocalSpacing
import com.abulnes.onboarding_presentation.components.ActionButton

@Composable
fun WelcomeScreen(onNext: ()-> Unit ,modifier: Modifier = Modifier) {
    val spacing = LocalSpacing.current
    Column(
        modifier = modifier.fillMaxSize().padding(spacing.spaceMedium),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = stringResource(id = R.string.welcome_text),
            textAlign = TextAlign.Center,
            style = MaterialTheme.typography.h1
        )
        Spacer(modifier = Modifier.height(spacing.spaceMedium))
        ActionButton(
            text = stringResource(id = R.string.next),
            onClick = { onNext() },
            modifier = Modifier.align(Alignment.CenterHorizontally)
        )
    }
}

