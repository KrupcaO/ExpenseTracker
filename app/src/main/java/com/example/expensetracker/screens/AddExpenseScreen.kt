package com.example.expensetracker.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.expensetracker.viewmodel.HomeViewModel
@Composable
fun AddExpenseScreen(homeViewModel: HomeViewModel, navController: NavController) {
    var amount by remember { mutableStateOf("") }
    var category by remember { mutableStateOf("") }
    var showValidationDialog by remember { mutableStateOf(false) }
    var validationMessage by remember { mutableStateOf("") }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        Text(
            text = "Přidat nový záznam",
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
                if (amount.isBlank() || !isValidAmount) {
                    validationMessage = "Zadejte částku větší než 0."
                    showValidationDialog = true
                } else if (category.isBlank()) {
                    validationMessage = "Vyplňte kategorii."
                    showValidationDialog = true
                } else {
                    homeViewModel.addExpense(amount.toDouble(), category)
                    amount = ""
                    category = ""
                    navController.navigate("expenseList")
                }
            },
            modifier = Modifier.align(Alignment.End)
        ) {
            Text("Přidat záznam")
        }
    }

    if (showValidationDialog) {
        AlertDialog(
            onDismissRequest = { showValidationDialog = false },
            title = { Text("Neplatné údaje") },
            text = { Text(validationMessage) },
            confirmButton = {
                TextButton(onClick = { showValidationDialog = false }) {
                    Text("OK")
                }
            }
        )
    }
}
