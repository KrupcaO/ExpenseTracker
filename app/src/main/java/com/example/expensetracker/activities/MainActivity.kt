package com.example.expensetracker.activities

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.expensetracker.screens.AddExpenseScreen
import com.example.expensetracker.screens.EditExpenseScreen
import com.example.expensetracker.screens.ExpenseListScreen
import com.example.expensetracker.screens.HomeScreen
import com.example.expensetracker.viewmodel.HomeViewModel

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val homeViewModel = HomeViewModel(application)

        setContent {
            val navController = rememberNavController()
            NavHost(
                navController = navController,
                startDestination = "home"
            ) {
                composable(route = "home") {
                    HomeScreen(navController = navController)
                }
                composable(route = "addExpense") {
                    AddExpenseScreen(homeViewModel = homeViewModel, navController = navController)
                }
                composable(route = "expenseList") {
                    ExpenseListScreen(homeViewModel = homeViewModel, navController = navController)
                }
                composable(
                    route = "editExpense/{expenseId}",
                    arguments = listOf(navArgument("expenseId") { type = NavType.IntType })
                ) { backStackEntry ->
                    val expenseId = backStackEntry.arguments?.getInt("expenseId") ?: -1
                    EditExpenseScreen(
                        homeViewModel = homeViewModel,
                        expenseId = expenseId,
                        navController = navController
                    )
                }
            }
        }
    }
}
