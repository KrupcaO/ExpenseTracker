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
fun ExpenseListScreen(homeViewModel: HomeViewModel, navController: NavController) {
    var filterText by remember { mutableStateOf("") }

    // Použijte collectAsState pro sledování StateFlow
    val expenses by homeViewModel.expenses.collectAsState(initial = emptyList())

    val filteredExpenses = expenses.filter {
        it.category.contains(filterText, ignoreCase = true)
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
                "Seznam výdajů",
                color = MaterialTheme.colorScheme.onSurface,
                style = MaterialTheme.typography.headlineMedium
            )

            // Filtrovací textové pole
            TextField(
                value = filterText,
                onValueChange = { filterText = it },
                label = { Text("Filtrovat podle kategorie") },
                colors = TextFieldDefaults.colors(
                    focusedContainerColor = MaterialTheme.colorScheme.surface,
                    unfocusedContainerColor = MaterialTheme.colorScheme.surface,
                    focusedTextColor = MaterialTheme.colorScheme.onSurface,
                    unfocusedTextColor = MaterialTheme.colorScheme.onSurface,
                    cursorColor = MaterialTheme.colorScheme.primary
                )
            )

            // Zobrazení seznamu výdajů
            for (expense in filteredExpenses) {
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 4.dp),
                    colors = CardDefaults.cardColors(
                        containerColor = MaterialTheme.colorScheme.surface
                    )
                ) {
                    Column(modifier = Modifier.padding(8.dp)) {
                        Text("Částka: ${expense.amount}", color = MaterialTheme.colorScheme.onSurface)
                        Text("Kategorie: ${expense.category}", color = MaterialTheme.colorScheme.onSurface)

                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(top = 8.dp),
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            // Tlačítko Upravit
                            Button(
                                onClick = {
                                    navController.navigate("editExpense/${expense.id}")
                                },
                                colors = ButtonDefaults.buttonColors(
                                    containerColor = MaterialTheme.colorScheme.primary
                                )
                            ) {
                                Text("Upravit")
                            }

                            // Tlačítko Smazat
                            Button(
                                onClick = {
                                    homeViewModel.deleteExpense(expense.id) // Smazání záznamu
                                },
                                colors = ButtonDefaults.buttonColors(
                                    containerColor = MaterialTheme.colorScheme.error,
                                    contentColor = MaterialTheme.colorScheme.onError
                                )
                            ) {
                                Text("Smazat")
                            }
                        }
                    }
                }
            }

            // Tlačítko pro přidání nového záznamu
            Button(
                onClick = { navController.navigate("addExpense") },
                modifier = Modifier.align(Alignment.End),
                colors = ButtonDefaults.buttonColors(
                    containerColor = MaterialTheme.colorScheme.primary,
                    contentColor = MaterialTheme.colorScheme.onPrimary
                )
            ) {
                Text("Přidat nový záznam")
            }
        }
    }
}
