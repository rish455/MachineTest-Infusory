package com.infusory.machinetest.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.infusory.machinetest.data.model.Model3D
import com.infusory.machinetest.data.model.ModelInstanceState
import com.infusory.machinetest.data.model.ModelMode
import com.infusory.machinetest.data.repository.ModelRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class MainViewModel(
    private val repository: ModelRepository
) : ViewModel() {

    private val _availableModels =
        MutableStateFlow<List<Model3D>>(emptyList())

    val availableModels: StateFlow<List<Model3D>> =
        _availableModels.asStateFlow()

    private val _activeModels =
        MutableStateFlow<List<ModelInstanceState>>(emptyList())

    val activeModels: StateFlow<List<ModelInstanceState>> =
        _activeModels.asStateFlow()

    init {
        viewModelScope.launch {
            _availableModels.value =
                repository.getAvailableModels()
        }
    }

    fun addModel(model: Model3D) {
        _activeModels.value =
            _activeModels.value +
                    ModelInstanceState(model = model)
    }

    fun removeModel(instanceId: String) {
        _activeModels.value =
            _activeModels.value.filterNot {
                it.instanceId == instanceId
            }
    }

    fun toggleInteraction(instanceId: String) {
        _activeModels.value =
            _activeModels.value.map {

                if (it.instanceId != instanceId) {
                    it
                } else {
                    it.copy(
                        mode =
                            if (it.mode == ModelMode.NORMAL)
                                ModelMode.INTERACTION
                            else
                                ModelMode.NORMAL
                    )
                }
            }
    }

    fun toggleLabels(instanceId: String) {
        _activeModels.value =
            _activeModels.value.map {

                if (it.instanceId == instanceId) {
                    it.copy(
                        labelsVisible =
                            !it.labelsVisible
                    )
                } else {
                    it
                }
            }
    }
}
