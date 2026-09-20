package com.infusory.machinetest.ui.viewer

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.infusory.machinetest.data.model.Model3D

@Composable
fun ModelPickerDialog(
    models: List<Model3D>,
    onDismiss: () -> Unit,
    onSelected: (Model3D) -> Unit
) {
    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text("Add model") },
        text = {
            Column {
                models.forEach { model ->
                    Text(
                        text = model.title,
                        modifier = Modifier
                            .fillMaxWidth()
                            .clickable { onSelected(model) }
                            .padding(vertical = 14.dp)
                    )
                }
            }
        },
        confirmButton = {
            TextButton(onClick = onDismiss) {
                Text("Cancel")
            }
        }
    )
}
