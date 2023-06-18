package com.glidotalks.moneyspent.home

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.AccountBalanceWallet
import androidx.compose.material.icons.rounded.Paid
import androidx.compose.material.icons.rounded.Wallet
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import timber.log.Timber

@Composable
fun HomeScreen(
    viewModel: HomeViewModel = hiltViewModel(),
    missingOnboardingInfo: () -> Unit
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    Timber.d("ui state %s", uiState)

    when (uiState) {
        HomeScreenUIState.AllConditionsMet -> {
            HomeLayout()
        }

        HomeScreenUIState.InProgress -> {

        }

        HomeScreenUIState.OnboardingMissing -> {
            missingOnboardingInfo()
        }
    }

    LaunchedEffect(key1 = Unit) {
        Timber.d("calling get user session")
        viewModel.getUserSession()
    }
}

@Composable
fun HomeLayout() {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight()
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(18.dp),
            contentAlignment = Alignment.Center
        ) {
            Text(
                textAlign = TextAlign.Center,
                text = "April 2023",
                style = MaterialTheme.typography.headlineLarge
            )
        }
        Column {
            MoneyCard(
                category = "Budget",
                amount = "40000",
                currency = "\u20B9",
                cardIcon = Icons.Rounded.Wallet,
                cardIconTint = 0xFFF39C12
            )
            MoneyCard(
                category = "Expense",
                amount = "14000",
                currency = "\u20B9",
                cardIcon = Icons.Rounded.Paid,
                cardIconTint = 0xFFD35400
            )
            MoneyCard(
                category = "Balance",
                amount = "26000",
                currency = "\u20B9",
                cardIcon = Icons.Rounded.AccountBalanceWallet,
                cardIconTint = 0xFF2ECC71
            )
        }
    }
}

@Composable
fun MoneyCard(
    category: String,
    amount: String,
    currency: String,
    cardIcon: ImageVector,
    cardIconTint: Long
) {
    Card(
        elevation = CardDefaults.cardElevation(
            defaultElevation = 1.dp
        ),
        modifier = Modifier
            .height(100.dp)
            .padding(
                start = 24.dp,
                top = 12.dp,
                end = 24.dp,
                bottom = 12.dp
            ),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.inverseOnSurface
        )
    ) {
        Row {
            Box(
                modifier = Modifier
                    .weight(1f)
                    .fillMaxHeight(),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = cardIcon,
                    contentDescription = null,
                    modifier = Modifier
                        .width(48.dp)
                        .height(48.dp),
                    tint = Color(color = cardIconTint)
                )
            }
            Box(
                contentAlignment = Alignment.Center,
                modifier = Modifier
                    .weight(1f)
                    .fillMaxHeight()
            ) {
                Column {
                    Text(
                        modifier = Modifier.padding(bottom = 4.dp),
                        text = category,
                        textAlign = TextAlign.Center,
                        style = MaterialTheme.typography.titleMedium
                    )
                    Row {
                        Text(
                            text = currency,
                            textAlign = TextAlign.Center,
                            style = MaterialTheme.typography.titleLarge
                        )
                        Text(
                            text = amount,
                            textAlign = TextAlign.Center,
                            style = MaterialTheme.typography.titleLarge
                        )
                    }
                }
            }
        }
    }
}

@Composable
@Preview
fun HomeLayoutPreview() {
    HomeLayout()
}