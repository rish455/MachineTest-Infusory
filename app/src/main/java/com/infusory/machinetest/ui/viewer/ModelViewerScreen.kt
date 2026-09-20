package com.infusory.machinetest.ui.viewer

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.infusory.machinetest.data.model.Model3D
import com.infusory.machinetest.data.model.ModelInstanceState

@Composable
fun ModelViewerScreen(
    models: List<ModelInstanceState>,
    availableModels: List<Model3D>,
    onAddModel: (Model3D) -> Unit,
    onRemoveModel: (String) -> Unit,
    onToggleInteraction: (String) -> Unit,
    onToggleLabels: (String) -> Unit
) {
    var showPicker by remember { mutableStateOf(false) }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFF111827))
    ) {
        models.forEachIndexed { index, model ->
            ModelContainer(
                state = model,
                initialIndex = index,
                onRemove = { onRemoveModel(model.instanceId) },
                onToggleInteraction = { onToggleInteraction(model.instanceId) },
                onToggleLabels = { onToggleLabels(model.instanceId) }
            )
        }

        FloatingActionButton(
            onClick = { showPicker = true },
            modifier = Modifier
                .align(Alignment.BottomEnd)
                .navigationBarsPadding()
                .padding(20.dp)
        ) {
            Text("+ Add")
        }

        if (showPicker) {
            ModelPickerDialog(
                models = availableModels,
                onDismiss = { showPicker = false },
                onSelected = {
                    onAddModel(it)
                    showPicker = false
                }
            )
        }
    }
}
