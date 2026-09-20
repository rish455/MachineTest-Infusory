package com.infusory.machinetest.ui

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import com.infusory.machinetest.data.repository.ModelRepository
import com.infusory.machinetest.ui.viewer.ModelViewerScreen

class MainActivity : ComponentActivity() {

    private val viewModel: MainViewModel by viewModels {
        MainViewModelFactory(
            ModelRepository()
        )
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            MaterialTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    MainContent()
                }
            }
        }
    }

    @Composable
    private fun MainContent() {

        val models by viewModel.activeModels.collectAsState()
        val availableModels by viewModel.availableModels.collectAsState()

        ModelViewerScreen(
            models = models,
            availableModels = availableModels,
            onAddModel = viewModel::addModel,
            onRemoveModel = viewModel::removeModel,
            onToggleInteraction = viewModel::toggleInteraction,
            onToggleLabels = viewModel::toggleLabels
        )
    }
}
