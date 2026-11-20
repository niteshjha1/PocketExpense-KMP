package com.example.pocketexpense

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import org.jetbrains.compose.ui.tooling.preview.Preview

data class Expense(
    val title: String,
    val category: String,
    val amount: Double
)

// Multiplatform-safe amount formatting
fun formatAmount(amount: Double): String {
    val whole = amount.toLong()
    val cents = ((amount - whole) * 100).toInt()
    return "€$whole.${cents.toString().padStart(2, '0')}"
}

@Composable
@Preview
fun App() {
    val expenses = remember {
        mutableStateListOf(
            Expense("Groceries", "Food", 45.80),
            Expense("Uber Ride", "Transport", 12.50),
            Expense("Coffee", "Food", 4.20),
            Expense("Netflix", "Entertainment", 13.99),
            Expense("Restaurant", "Food", 32.00)
        )
    }
    val totalSpent = expenses.sumOf { it.amount }
    val monthlyBudget = 1500.0
    MaterialTheme {
        Surface(
            modifier = Modifier.fillMaxSize(),
            color = Color(0xFFF5F6FA)
        ) {
            LazyColumn(
                modifier = Modifier.fillMaxSize().padding(horizontal = 20.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                item {
                    Spacer(modifier = Modifier.height(24.dp))
                    Text(
                        text = "PocketExpense",
                        fontSize = 28.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color(0xFF172033)
                    )
                    Text(
                        text = "Your personal finance overview",
                        color = Color.Gray,
                        fontSize = 14.sp
                    )
                }
                item {
                    // Monthly spending card
                    Card(
                        modifier = Modifier.fillMaxWidth(),
                        shape = RoundedCornerShape(24.dp),
                        colors = CardDefaults.cardColors(containerColor = Color(0xFF263B80))
                    ) {
                        Column(modifier = Modifier.padding(24.dp)) {
                            Text(
                                text = "TOTAL SPENT THIS MONTH",
                                color = Color(0xFFCFD8F5),
                                fontSize = 13.sp
                            )
                            Spacer(modifier = Modifier.height(8.dp))
                            Text(
                                text = formatAmount(totalSpent),
                                color = Color.White,
                                fontSize = 36.sp,
                                fontWeight = FontWeight.Bold
                            )
                            Spacer(modifier = Modifier.height(20.dp))
                            Text(
                                text = "Monthly budget: ${formatAmount(monthlyBudget)}",
                                color = Color.White,
                                fontSize = 14.sp
                            )
                            Spacer(modifier = Modifier.height(10.dp))
                            LinearProgressIndicator(
                                progress = { (totalSpent / monthlyBudget).toFloat().coerceIn(0f, 1f) },
                                modifier = Modifier.fillMaxWidth(),
                                color = Color(0xFF8FB5FF),
                                trackColor = Color(0xFF53679E)
                            )
                            Spacer(modifier = Modifier.height(8.dp))
                            Text(
                                text = "${formatAmount(monthlyBudget - totalSpent)} remaining",
                                color = Color(0xFFCFD8F5),
                                fontSize = 13.sp
                            )
                        }
                    }
                }
                item {
                    Text(
                        text = "Overview",
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color(0xFF172033)
                    )
                }
                item {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(12.dp)
                    ) {
                        SummaryCard(
                            title = "Transactions",
                            value = expenses.size.toString(),
                            modifier = Modifier.weight(1f)
                        )
                        SummaryCard(
                            title = "Average",
                            value = formatAmount(if (expenses.isNotEmpty()) totalSpent / expenses.size else 0.0),
                            modifier = Modifier.weight(1f)
                        )
                    }
                }
                item {
                    Text(
                        text = "Recent transactions",
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color(0xFF172033)
                    )
                }
                items(expenses) { expense ->
                    ExpenseItem(expense)
                }
                item {
                    // Adds a sample transaction to demonstrate state updates
                    Button(
                        onClick = {
                            expenses.add(Expense("Lunch", "Food", 18.50))
                        },
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Text("Add sample expense + €18.50")
                    }
                    Spacer(modifier = Modifier.height(24.dp))
                }
            }
        }
    }
}

@Composable
fun SummaryCard(
    title: String,
    value: String,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier,
        shape = RoundedCornerShape(20.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White)
    ) {
        Column(modifier = Modifier.padding(18.dp)) {
            Text(title, color = Color.Gray, fontSize = 13.sp)
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                value,
                color = Color(0xFF172033),
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold
            )
        }
    }
}

@Composable
fun ExpenseItem(expense: Expense) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(18.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth().padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            // Category icon placeholder
            Surface(
                shape = RoundedCornerShape(14.dp),
                color = Color(0xFFE9EDFA)
            ) {
                Text(
                    text = expense.category.take(1),
                    modifier = Modifier.padding(12.dp),
                    color = Color(0xFF263B80),
                    fontWeight = FontWeight.Bold
                )
            }
            Spacer(modifier = Modifier.width(14.dp))
            Column(modifier = Modifier.weight(1f)) {
                Text(
                    expense.title,
                    fontWeight = FontWeight.SemiBold,
                    color = Color(0xFF172033)
                )
                Text(
                    expense.category,
                    fontSize = 13.sp,
                    color = Color.Gray
                )
            }
            Text(
                text = "- ${formatAmount(expense.amount)}",
                fontWeight = FontWeight.Bold,
                color = Color(0xFF172033)
            )
        }
    }
}