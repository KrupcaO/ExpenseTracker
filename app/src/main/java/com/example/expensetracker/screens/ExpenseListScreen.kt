package com.example.expensetracker.screens

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.*

import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.expensetracker.viewmodel.HomeViewModel

@SuppressLint("StateFlowValueCalledInComposition")
@Composable
fun ExpenseListScreen(homeViewModel: HomeViewModel, navController: NavController) {
    var filterText by remember { mutableStateOf("") }

    val totalAmount = homeViewModel.getTotalAmount()
    val filteredExpenses = if (filterText.isNotBlank()) {
        homeViewModel.expenses.value.filter {
            it.category.contains(filterText, ignoreCase = true)
        }
    } else {
        homeViewModel.expenses.value
    }

    val totalAmountByCategory = if (filterText.isNotBlank()) {
        homeViewModel.getTotalAmountByCategory(filterText)
    } else {
        totalAmount
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        Text(
            text = "Celková částka: $totalAmount Kč",
            style = MaterialTheme.typography.bodyLarge
        )

        Text(
            text = "Celková částka (${if (filterText.isNotBlank()) filterText else "vše"}): $totalAmountByCategory Kč",
            style = MaterialTheme.typography.bodyLarge
        )

        TextField(
            value = filterText,
            onValueChange = { filterText = it },
            label = { Text("Filtrovat podle kategorie") },
            modifier = Modifier.fillMaxWidth()
        )

        for (expense in filteredExpenses) {
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 4.dp)
            ) {
                Column(modifier = Modifier.padding(8.dp)) {
                    Text("Částka: ${expense.amount} Kč", style = MaterialTheme.typography.bodyLarge)
                    Text("Kategorie: ${expense.category}", style = MaterialTheme.typography.bodyMedium)
                }
            }
        }

        Button(
            onClick = { navController.navigate("addExpense") },
            modifier = Modifier.align(Alignment.Start)
        ) {
            Text("Přidat nový záznam")
        }
    }
}


