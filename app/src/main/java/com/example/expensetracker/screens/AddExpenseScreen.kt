package com.example.expensetracker.screens

import androidx.compose.foundation.background
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

    // Nastavení pozadí
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.surface)
            .padding(16.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize(),
            verticalArrangement = Arrangement.spacedBy(8.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "Add new record",
                style = MaterialTheme.typography.headlineMedium,
                color = MaterialTheme.colorScheme.onSurface
            )

            TextField(
                value = amount,
                onValueChange = { amount = it },
                label = { Text("Amount", color = MaterialTheme.colorScheme.onSurface) },
                colors = TextFieldDefaults.colors(
                    focusedContainerColor = MaterialTheme.colorScheme.surface,
                    unfocusedContainerColor = MaterialTheme.colorScheme.surface,
                    focusedTextColor = MaterialTheme.colorScheme.onSurface,
                    unfocusedTextColor = MaterialTheme.colorScheme.onSurface,
                    cursorColor = MaterialTheme.colorScheme.primary
                )
            )

            TextField(
                value = category,
                onValueChange = { category = it },
                label = { Text("Category", color = MaterialTheme.colorScheme.onSurface) },
                colors = TextFieldDefaults.colors(
                    focusedContainerColor = MaterialTheme.colorScheme.surface,
                    unfocusedContainerColor = MaterialTheme.colorScheme.surface,
                    focusedTextColor = MaterialTheme.colorScheme.onSurface,
                    unfocusedTextColor = MaterialTheme.colorScheme.onSurface,
                    cursorColor = MaterialTheme.colorScheme.primary
                )
            )

            Button(
                onClick = {
                    val isValidAmount = amount.toDoubleOrNull() ?: 0.0 > 0
                    if (amount.isBlank() || !isValidAmount) {
                        validationMessage = "Enter an amount greater than 0."
                        showValidationDialog = true
                    } else if (category.isBlank()) {
                        validationMessage = "Fill in the category."
                        showValidationDialog = true
                    } else {
                        homeViewModel.addExpense(amount.toDouble(), category)
                        navController.navigate("expenseList")
                    }
                },
                modifier = Modifier.align(Alignment.End),
                colors = ButtonDefaults.buttonColors(
                    containerColor = MaterialTheme.colorScheme.primary,
                    contentColor = MaterialTheme.colorScheme.onPrimary
                )
            ) {
                Text("Add record")
            }
        }

        // Dialog pro validaci
        if (showValidationDialog) {
            AlertDialog(
                onDismissRequest = { showValidationDialog = false },
                title = { Text("Invalid data") },
                text = { Text(validationMessage) },
                confirmButton = {
                    TextButton(onClick = { showValidationDialog = false }) {
                        Text("OK")
                    }
                }
            )
        }
    }
}