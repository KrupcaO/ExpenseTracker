package com.example.expensetracker.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.expensetracker.ui.theme.ThemeState

@Composable
fun HomeScreen(navController: NavController) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.surface) // Pozadí
    ) {
        // Tlačítko pro přepínání motivu v pravém horním rohu
        Button(
            onClick = { ThemeState.toggleTheme() },
            modifier = Modifier
                .align(Alignment.TopEnd)
                .padding(16.dp)
        ) {
            Text(
                text = if (ThemeState.isDarkTheme.value) "Světlý režim" else "Tmavý režim",
                color = MaterialTheme.colorScheme.onPrimary
            )
        }

        // Hlavní obsah obrazovky
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "Vítejte v aplikaci Expense Tracker!",
                color = MaterialTheme.colorScheme.onSurface
            )

            Spacer(modifier = Modifier.height(16.dp))

            // Tlačítko pro přidání záznamu
            Button(
                onClick = { navController.navigate("addExpense") },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Přidat nový záznam")
            }

            Spacer(modifier = Modifier.height(8.dp))

            // Tlačítko pro zobrazení seznamu výdajů
            Button(
                onClick = { navController.navigate("expenseList") },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Zobrazit seznam výdajů")
            }
        }
    }
}
