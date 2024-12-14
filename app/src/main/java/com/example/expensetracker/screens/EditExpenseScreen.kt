package com.example.expensetracker.screens

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.expensetracker.model.Expense
import com.example.expensetracker.viewmodel.HomeViewModel

@SuppressLint("StateFlowValueCalledInComposition")
@Composable
fun EditExpenseScreen(homeViewModel: HomeViewModel, expenseId: Int, navController: NavController) {
    val expense = homeViewModel.expenses.value.find { it.id == expenseId }
    var amount by remember { mutableStateOf(expense?.amount?.toString() ?: "") }
    var category by remember { mutableStateOf(expense?.category ?: "") }
    var showValidationDialog by remember { mutableStateOf(false) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        Text(
            text = "Editovat záznam",
            style = MaterialTheme.typography.headlineMedium,
            modifier = Modifier.align(Alignment.CenterHorizontally)
        )

        TextField(
            value = amount,
            onValueChange = { amount = it },
            label = { Text("Částka") },
            modifier = Modifier.fillMaxWidth()
        )

        TextField(
            value = category,
            onValueChange = { category = it },
            label = { Text("Kategorie") },
            modifier = Modifier.fillMaxWidth()
        )

        Button(
            onClick = {
                val isValidAmount = amount.toDoubleOrNull() ?: 0.0 > 0
                if (amount.isNotBlank() && category.isNotBlank() && isValidAmount) {
                    val updatedExpense = Expense(
                        id = expenseId,
                        amount = amount.toDouble(),
                        category = category
                    )
                    homeViewModel.updateExpense(updatedExpense)
                    navController.popBackStack() // Vrátí se na seznam
                } else {
                    showValidationDialog = true
                }
            },
            modifier = Modifier.align(Alignment.End)
        ) {
            Text("Uložit změny")
        }
    }

    // Dialog pro validaci
    if (showValidationDialog) {
        AlertDialog(
            onDismissRequest = { showValidationDialog = false },
            title = { Text("Neplatné údaje") },
            text = { Text("Zadejte platnou částku (větší než 0) a kategorii.") },
            confirmButton = {
                TextButton(onClick = { showValidationDialog = false }) {
                    Text("OK")
                }
            }
        )
    }
}
