package com.dinyairsadot.taxtracker.feature.category

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import android.graphics.Color as AndroidColor



private data class PresetCategoryColor(
    val hex: String,
    val color: Color
)

// Shared preset colors for categories.
// Hex values are valid #RRGGBB and work with existing validation.
private val presetCategoryColors = listOf(
    PresetCategoryColor("#FF9800", Color(0xFFFF9800)), // Orange
    PresetCategoryColor("#F44336", Color(0xFFF44336)), // Red
    PresetCategoryColor("#E91E63", Color(0xFFE91E63)), // Pink
    PresetCategoryColor("#9C27B0", Color(0xFF9C27B0)), // Purple
    PresetCategoryColor("#3F51B5", Color(0xFF3F51B5)), // Indigo
    PresetCategoryColor("#03A9F4", Color(0xFF03A9F4)), // Light Blue
    PresetCategoryColor("#4CAF50", Color(0xFF4CAF50)), // Green
    PresetCategoryColor("#CDDC39", Color(0xFFCDDC39)), // Lime
    PresetCategoryColor("#795548", Color(0xFF795548))  // Brown
)


@Composable
fun CategoryColorOptionsRow(
    selectedColorHex: String,
    onColorSelected: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier,
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        presetCategoryColors.forEach { preset ->
            val isSelected = selectedColorHex.equals(preset.hex, ignoreCase = true)

            Box(
                modifier = Modifier
                    .size(32.dp)
                    .clip(CircleShape)
                    .background(preset.color)
                    .border(
                        width = if (isSelected) 3.dp else 1.dp,
                        color = if (isSelected) {
                            MaterialTheme.colorScheme.primary
                        } else {
                            MaterialTheme.colorScheme.outline
                        },
                        shape = CircleShape
                    )
                    .clickable {
                        onColorSelected(preset.hex)
                    }
            )
        }
    }
}

private fun parseCategoryColorOrNull(hex: String): androidx.compose.ui.graphics.Color? {
    val trimmed = hex.trim()
    val regex = Regex("^#[0-9A-Fa-f]{6}$")
    if (!regex.matches(trimmed)) return null

    return try {
        val intColor = AndroidColor.parseColor(trimmed)
        androidx.compose.ui.graphics.Color(intColor)
    } catch (e: IllegalArgumentException) {
        null
    }
}

@Composable
fun CategoryColorPreview(
    colorHex: String,
    modifier: Modifier = Modifier
) {
    val parsedColor = parseCategoryColorOrNull(colorHex)
    val fillColor = parsedColor ?: MaterialTheme.colorScheme.surfaceVariant

    Box(
        modifier = modifier
            .size(28.dp)
            .clip(CircleShape)
            .background(fillColor)
            .border(
                width = 1.dp,
                color = MaterialTheme.colorScheme.outline,
                shape = CircleShape
            )
    )
}

