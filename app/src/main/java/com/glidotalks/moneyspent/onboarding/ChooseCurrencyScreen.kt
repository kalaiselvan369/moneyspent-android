package com.glidotalks.moneyspent.onboarding

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.Checkbox
import androidx.compose.material3.Divider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.flowWithLifecycle
import com.glidotalks.moneyspent.views.MaterialButton
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.launch
import timber.log.Timber

@Composable
fun SetCurrencyScreen(
    viewModel: ChooseCurrencyViewModel,
    nextButtonClicked: () -> Unit,
) {

    val finishButtonState = viewModel.uiStateFlow.collectAsStateWithLifecycle()

    val scope = rememberCoroutineScope()
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
                text = "Choose currency",
                textAlign = TextAlign.Center,
                style = MaterialTheme.typography.headlineLarge
            )
        }
        LazyColumn(
            modifier = Modifier
                .padding(24.dp)
                .weight(2f)
                .fillMaxWidth()
        ) {
            itemsIndexed(viewModel.currencyList) { index, currency ->
                CurrencyItem(currency = currency) {
                    viewModel.updateItem(index, currency)
                }
                Divider()
            }
        }
        Box(
            modifier = Modifier
                .weight(1f)
                .fillMaxWidth(),
            contentAlignment = Alignment.Center
        ) {
            MaterialButton(
                buttonText = "Finish",
                enabled = finishButtonState.value
            ) {
                scope.launch {
                    viewModel.handleFinishButtonClick()
                }
            }
        }
    }

    val lifecycle = LocalLifecycleOwner.current.lifecycle
    val onNavigateToNext by rememberUpdatedState(nextButtonClicked)

    LaunchedEffect(viewModel, lifecycle) {
        // Whenever the uiState changes, check if next button clicked and
        // call the `onNextButtonClick` event when `lifecycle` is at least STARTED
        snapshotFlow { viewModel.uiState }
            .filter {
                it.isCurrencySaved
            }
            .flowWithLifecycle(lifecycle)
            .collect {
                Timber.d("navigate to next")
                onNavigateToNext()
            }
    }
}

@Composable
fun CurrencyItem(
    currency: CurrencyItem,
    onItemClick: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(56.dp)
            .padding(start = 16.dp, end = 24.dp)
            .clickable {
                onItemClick()
            },
        verticalAlignment = Alignment.CenterVertically
    ) {
        Box(
            modifier = Modifier
                .fillMaxHeight()
                .weight(1f),
            contentAlignment = Alignment.CenterStart
        ) {
            Text(
                text = currency.name,
                style = MaterialTheme.typography.bodyLarge
            )
        }
        Checkbox(
            modifier = Modifier.padding(start = 16.dp),
            checked = currency.isChecked,
            onCheckedChange = { _ -> onItemClick() }
        )
    }
}

@Preview
@Composable
fun CurrencyItemPreview() {
    CurrencyItem(currency = CurrencyItem(name = "Rupee", isChecked = false)) {

    }
}