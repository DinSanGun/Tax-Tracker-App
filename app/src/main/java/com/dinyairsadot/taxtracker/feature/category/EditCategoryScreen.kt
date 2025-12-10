package com.dinyairsadot.taxtracker.feature.category

import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EditCategoryScreen(
    initialName: String,
    initialColorHex: String,
    initialDescription: String?,
    otherNamesLower: Set<String>,
    onNavigateBack: () -> Unit,
    onSaveCategory: (name: String, colorHex: String, description: String) -> Unit,
    onDeleteCategory: () -> Unit
) {
    var name by rememberSaveable { mutableStateOf(initialName) }
    var colorHex by rememberSaveable { mutableStateOf(initialColorHex) }
    var description by rememberSaveable { mutableStateOf(initialDescription.orEmpty()) }

    var nameError by remember { mutableStateOf<String?>(null) }
    var colorError by remember { mutableStateOf<String?>(null) }

    var showDeleteDialog by remember { mutableStateOf(false) }

    fun onSaveClicked() {
        var hasError = false

        if (name.isBlank()) {
            nameError = "Name is required"
            hasError = true
        } else if (otherNamesLower.contains(name.trim().lowercase())) {
            nameError = "Name must be unique"
            hasError = true
        }

        if (colorHex.isNotBlank()) {
            val regex = Regex("^#[0-9A-Fa-f]{6}$")
            if (!regex.matches(colorHex.trim())) {
                colorError = "Color must be in #RRGGBB format"
                hasError = true
            }
        }

        if (!hasError) {
            onSaveCategory(
                name.trim(),
                colorHex.trim(),
                description.trim()
            )
            onNavigateBack()
        }
    }

    val formState = CategoryFormState(
        name = name,
        nameError = nameError,
        colorHex = colorHex,
        colorError = colorError,
        description = description
    )

    val formCallbacks = CategoryFormCallbacks(
        onNameChange = { newName ->
            name = newName
            if (nameError != null) nameError = null
        },
        onColorHexChange = { newColor ->
            colorHex = newColor
            if (colorError != null) colorError = null
        },
        onDescriptionChange = { newDesc ->
            description = newDesc
        },
        onSaveClick = { onSaveClicked() },
        onDeleteClick = { showDeleteDialog = true }
    )

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Edit category") },
                navigationIcon = {
                    IconButton(onClick = onNavigateBack) {
                        Icon(
                            imageVector = Icons.Default.ArrowBack,
                            contentDescription = "Back"
                        )
                    }
                }
            )
        }
    ) { innerPadding ->
        CategoryForm(
            state = formState,
            callbacks = formCallbacks,
            saveButtonLabel = "Save changes",
            modifier = Modifier.padding(innerPadding)
        )
    }

    if (showDeleteDialog) {
        AlertDialog(
            onDismissRequest = { showDeleteDialog = false },
            title = { Text("Delete category?") },
            text = {
                Text(
                    "Are you sure you want to delete this category? " +
                            "All data associated with it will be removed."
                )
            },
            confirmButton = {
                TextButton(
                    onClick = {
                        showDeleteDialog = false
                        onDeleteCategory()
                        onNavigateBack()
                    }
                ) {
                    Text("Delete")
                }
            },
            dismissButton = {
                TextButton(onClick = { showDeleteDialog = false }) {
                    Text("Cancel")
                }
            }
        )
    }
}