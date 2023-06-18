package com.glidotalks.moneyspent.onboarding

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.lifecycle.flowWithLifecycle
import com.glidotalks.moneyspent.R
import com.glidotalks.moneyspent.views.MaterialButton
import kotlinx.coroutines.flow.filter

@Composable
fun WelcomeScreen(
    viewModel: WelcomeViewModel,
    navigateToNextScreen: () -> Unit,
) {

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Box(
            modifier = Modifier
                .weight(1f)
                .fillMaxWidth(),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = "Welcome",
                textAlign = TextAlign.Center,
                style = MaterialTheme.typography.headlineLarge
            )
        }

        Column(
            modifier = Modifier
                .weight(2f)
                .fillMaxWidth()
                .padding(24.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {

            Image(
                painter = painterResource(id = R.drawable.ic_waving_hand_48dp),
                contentDescription = LocalContext.current.getString(R.string.app_name),
                modifier = Modifier
                    .width(96.dp)
                    .height(96.dp)
            )

            Spacer(modifier = Modifier.height(24.dp))

            Text(
                text = "MoneySpent-Lite is completely offline app. \n\n Any data you enter will be saved in the app storage only. Hence, uninstalling the app or clearing app's cache/memory will permanently delete all your information.",
                textAlign = TextAlign.Center,
                style = MaterialTheme.typography.bodyLarge
            )
        }

        Box(
            modifier = Modifier
                .weight(1f)
                .fillMaxWidth(),
            contentAlignment = Alignment.Center
        ) {
            MaterialButton(
                buttonText = "Next",
                enabled = true
            ) {
                viewModel.handleNextButtonClick()
            }
        }
    }


    val lifecycle = LocalLifecycleOwner.current.lifecycle
    val onNavigateToNext by rememberUpdatedState(navigateToNextScreen)

    LaunchedEffect(viewModel, lifecycle) {
        // Whenever the uiState changes, check if next button clicked and
        // call the `onNextButtonClick` event when `lifecycle` is at least STARTED
        snapshotFlow { viewModel.uiState }
            .filter {
                it.isDisclaimerRead
            }
            .flowWithLifecycle(lifecycle)
            .collect {
                onNavigateToNext()
            }
    }
}