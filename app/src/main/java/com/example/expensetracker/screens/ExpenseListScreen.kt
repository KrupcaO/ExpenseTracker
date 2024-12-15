package com.example.expensetracker.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.expensetracker.model.Expense
import com.example.expensetracker.viewmodel.HomeViewModel

@Composable
fun ExpenseListScreen(homeViewModel: HomeViewModel, navController: NavController) {
    var filterText by remember { mutableStateOf("") }

    val expenses by homeViewModel.expenses.collectAsState(initial = emptyList())
    val totalAmount by remember { derivedStateOf { homeViewModel.getTotalAmount() } }
    val totalAmountByCategory by remember {
        derivedStateOf { homeViewModel.getTotalAmountByCategory(filterText) }
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.surface)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Text(
                text = "Celková částka: ${"%.2f".format(totalAmount)} Kč",
                style = MaterialTheme.typography.bodyLarge,
                color = MaterialTheme.colorScheme.onSurface
            )

            TextField(
                value = filterText,
                onValueChange = { filterText = it },
                label = { Text("Filtrovat podle kategorie") },
                modifier = Modifier.fillMaxWidth()
            )

            Text(
                text = "Součet pro kategorii '${filterText}': ${"%.2f".format(totalAmountByCategory)} Kč",
                style = MaterialTheme.typography.bodyLarge,
                color = MaterialTheme.colorScheme.onSurface
            )

            LazyColumn {
                items(expenses.filter { it.category.contains(filterText, ignoreCase = true) }) { expense ->
                    ExpenseItem(expense = expense, navController = navController)
                }
            }

            Spacer(modifier = Modifier.height(8.dp))

            Button(
                onClick = { navController.navigate("addExpense") },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Přidat nový záznam")
            }
        }
    }
}

@Composable
fun ExpenseItem(expense: Expense, navController: NavController) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(4.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface)
    ) {
        Row(
            modifier = Modifier.padding(8.dp),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Column {
                Text("Částka: ${"%.2f".format(expense.amount)} Kč", color = MaterialTheme.colorScheme.onSurface)
                Text("Kategorie: ${expense.category}", color = MaterialTheme.colorScheme.onSurface)
            }
            // Tlačítko pro úpravu záznamu
            Button(
                onClick = { navController.navigate("editExpense/${expense.id}") },
                colors = ButtonDefaults.buttonColors(
                    containerColor = MaterialTheme.colorScheme.primary,
                    contentColor = MaterialTheme.colorScheme.onPrimary
                )
            ) {
                Text("Upravit")
            }
        }
    }
}
