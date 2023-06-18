package com.glidotalks.moneyspent.expense

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AddExpenseViewModel @Inject constructor() : ViewModel() {

    val options = mutableStateOf<List<String>>(
        listOf(
            "Rent",
            "Fuel",
            "Medicine",
            "Travel",
            "Groceries",
            "Restaurant",
            "Fruits",
            "Vegetables",
            "Fee",
            "Repair"
        )
    )

    val dateSelected = mutableStateOf<String>("DD/MM/YY")

    fun onDateModified(year: Int, month: Int, day: Int) {
        viewModelScope.launch {
            dateSelected.value = "09/06/2023"
        }
    }
}

