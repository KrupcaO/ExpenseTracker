package com.example.expensetracker.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.expensetracker.model.AppDatabase
import com.example.expensetracker.model.Expense
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class HomeViewModel(application: Application) : AndroidViewModel(application) {
    private val expenseDao = AppDatabase.getDatabase(application).expenseDao()

    private val _expenses = MutableStateFlow<List<Expense>>(emptyList())
    val expenses: StateFlow<List<Expense>> get() = _expenses

    init {
        loadExpenses()
    }

    fun loadExpenses() {
        viewModelScope.launch {
            _expenses.value = expenseDao.getAllExpenses()
        }
    }

    fun addExpense(amount: Double, category: String) {
        viewModelScope.launch {
            val newExpense = Expense(amount = amount, category = category)
            expenseDao.addExpense(newExpense)
            loadExpenses()
        }
    }

    fun getTotalAmount(): Double {
        return expenses.value.sumOf { it.amount }
    }

    fun getTotalAmountByCategory(category: String): Double {
        return expenses.value.filter { it.category.equals(category, ignoreCase = true) }
            .sumOf { it.amount }
    }


    fun updateExpense(expense: Expense) {
        viewModelScope.launch {
            expenseDao.updateExpense(expense)
            loadExpenses()
        }
    }

    fun deleteExpense(expenseId: Int) {
        viewModelScope.launch {
            expenseDao.deleteExpenseById(expenseId)
            loadExpenses()
        }
    }
}
