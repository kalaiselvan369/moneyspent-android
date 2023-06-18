package com.glidotalks.moneyspent.budget

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.*
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.glidotalks.moneyspent.views.MaterialButton
import com.glidotalks.moneyspent.views.MaterialTextField

@Composable
fun BudgetScreen(viewModel: BudgetViewModel) {

    val uiState by viewModel.budgetStateFlow.collectAsStateWithLifecycle()

    val budgetInput by viewModel.budgetInputStateFlow

    val buttonState by viewModel.buttonState

    var description by remember {
        mutableStateOf("Allocate the budget for current month. You can update the budget amount any number of times within a month.")
    }

    var amountLabel by remember {
        mutableStateOf("Enter amount")
    }

    var buttonText by remember {
        mutableStateOf("Save")
    }

    when (uiState) {
        is BudgetUIState.ExistingBudget -> {
            description =
                "Your current budget amount is ${(uiState as BudgetUIState.ExistingBudget).budget.amount}."
            amountLabel = "Update amount"
            buttonText = "Update"
        }

        BudgetUIState.Loading -> {}
    }

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
                text = "Allocate Budget",
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
            Text(
                textAlign = TextAlign.Center,
                text = description,
                modifier = Modifier.padding(bottom = 48.dp),
                style = MaterialTheme.typography.bodyLarge
            )
            MaterialTextField(
                text = budgetInput,
                label = amountLabel,
                keyboardOptions = KeyboardOptions.Default.copy(
                    imeAction = ImeAction.Done,
                    keyboardType = KeyboardType.Number
                ),
                onInputChange = {
                    viewModel.amountEntered(it)
                }
            )
        }
        Box(
            modifier = Modifier
                .weight(1f)
                .fillMaxWidth(),
            contentAlignment = Alignment.Center
        ) {
            MaterialButton(
                buttonText = buttonText,
                enabled = buttonState
            ) {
                viewModel.setBudget()
            }
        }
    }

    LaunchedEffect(Unit) {
        viewModel.getCurrentBudget()
    }
}

