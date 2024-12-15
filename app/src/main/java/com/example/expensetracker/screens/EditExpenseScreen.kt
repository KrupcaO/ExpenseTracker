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
    var validationMessage by remember { mutableStateOf("") }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        Text(
            text = "Edit item",
            style = MaterialTheme.typography.headlineMedium,
            modifier = Modifier.align(Alignment.CenterHorizontally)
        )

        TextField(
            value = amount,
            onValueChange = { amount = it },
            label = { Text("Amount") },
            colors = TextFieldDefaults.colors(
                focusedContainerColor = MaterialTheme.colorScheme.surface,
                unfocusedContainerColor = MaterialTheme.colorScheme.surface
            )
        )

        TextField(
            value = category,
            onValueChange = { category = it },
            label = { Text("Category") },
            colors = TextFieldDefaults.colors(
                focusedContainerColor = MaterialTheme.colorScheme.surface,
                unfocusedContainerColor = MaterialTheme.colorScheme.surface
            )
        )

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 8.dp),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            // Tlačítko pro smazání záznamu
            Button(
                onClick = {
                    homeViewModel.deleteExpense(expenseId) // Smazání záznamu
                    navController.popBackStack() // Návrat na předchozí stránku
                },
                colors = ButtonDefaults.buttonColors(
                    containerColor = MaterialTheme.colorScheme.error,
                    contentColor = MaterialTheme.colorScheme.onError
                )
            ) {
                Text("Delete")
            }

            // Tlačítko pro uložení změn
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
                        val updatedExpense = Expense(
                            id = expenseId,
                            amount = amount.toDouble(),
                            category = category
                        )
                        homeViewModel.updateExpense(updatedExpense) // Aktualizace záznamu
                        navController.popBackStack()
                    }
                }
            ) {
                Text("Save changes")
            }
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