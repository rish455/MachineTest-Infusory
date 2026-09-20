package com.infusory.machinetest.ui.viewer

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@Composable
fun ModelControls(
    interactionEnabled: Boolean,
    labelsVisible: Boolean,
    onToggleInteraction: () -> Unit,
    onToggleLabels: () -> Unit,
    onRemove: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(6.dp),
        horizontalArrangement = Arrangement.spacedBy(6.dp)
    ) {
        Button(
            onClick = onToggleInteraction,
            colors = ButtonDefaults.buttonColors(
                containerColor = if (interactionEnabled) Color(0xFF16A34A) else Color(0xFF334155)
            )
        ) {
            Text(if (interactionEnabled) "Interact ON" else "Interact")
        }

        Button(
            onClick = onToggleLabels,
            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF334155))
        ) {
            Text(if (labelsVisible) "Labels ON" else "Labels")
        }

        Button(
            onClick = onRemove,
            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFB91C1C))
        ) {
            Text("×")
        }
    }
}
