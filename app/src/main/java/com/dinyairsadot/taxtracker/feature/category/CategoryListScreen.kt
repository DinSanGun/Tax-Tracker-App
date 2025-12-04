package com.dinyairsadot.taxtracker.feature.category

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.dinyairsadot.taxtracker.core.domain.Category
import com.dinyairsadot.taxtracker.core.domain.PaymentStatus
import androidx.core.graphics.toColorInt


// Temporary dummy data – later this will come from user's data
private val dummyCategories = listOf(
    Category(
        id = 1,
        name = "Electricity",
        colorHex = "#FF9800",
        description = "Electricity provider bills"
    ),
    Category(
        id = 2,
        name = "Water",
        colorHex = "#2196F3",
        description = "Water and sewage"
    ),
    Category(
        id = 3,
        name = "City Taxes",
        colorHex = "#4CAF50",
        description = "Arnona / city hall payments"
    ),
)
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CategoryListScreen(
    // Later we’ll add parameters

) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Categories") }
            )
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = {
                    // TODO: navigate to "Add Category" screen
                }
            ) {
                Icon(
                    imageVector = Icons.Default.Add,
                    contentDescription = "Add category"
                )
            }
        }
    ) { innerPadding ->
        CategoryListContent(
            categories = dummyCategories,
            modifier = Modifier.padding(innerPadding)
        )
    }
}

@Composable
private fun CategoryListContent(
    categories: List<Category>,
    modifier: Modifier = Modifier
) {
    if (categories.isEmpty()) {
        // Empty state – later we can make this nicer
        Box(
            modifier = modifier
                .fillMaxSize()
                .padding(16.dp),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = "No categories yet.\nTap + to add your first category.",
                style = MaterialTheme.typography.bodyLarge
            )
        }
    } else {
        LazyColumn(
            modifier = modifier
                .fillMaxSize()
                .padding(8.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            items(categories) { category ->
                CategoryItem(
                    category = category,
                    onClick = {
                        // TODO: navigate to that category's invoice list
                    }
                )
            }
        }
    }
}

@Composable
private fun CategoryItem(
    category: Category,
    onClick: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onClick() }
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
        ) {
            // Color stripe on the left
            Box(
                modifier = Modifier
                    .width(8.dp)
                    .height(IntrinsicSize.Min)
                    .background(parseColor(category.colorHex))
            )

            Column(
                modifier = Modifier
                    .padding(16.dp)
                    .fillMaxWidth()
            ) {
                Text(
                    text = category.name,
                    style = MaterialTheme.typography.titleMedium.copy(
                        fontWeight = FontWeight.Bold
                    )
                )
                if (!category.description.isNullOrBlank()) {
                    Spacer(modifier = Modifier.height(4.dp))
                    Text(
                        text = category.description,
                        style = MaterialTheme.typography.bodyMedium
                    )
                }

                // Placeholder for small stats – later we’ll populate from DB
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = "3 unpaid · 5 total invoices", // TODO: replace with real data
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.primary
                )
            }
        }
    }
}

/**
 * Very small helper to convert #RRGGBB to a Color.
 * Later we might move this to a shared util.
 */
private fun parseColor(hex: String): Color {
    return try {
        Color(hex.toColorInt())
    } catch (e: IllegalArgumentException) {
        // Fallback color if parsing fails
        Color.Gray
    }
}
