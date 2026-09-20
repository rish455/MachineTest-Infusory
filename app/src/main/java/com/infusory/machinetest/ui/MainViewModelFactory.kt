package com.infusory.machinetest.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.infusory.machinetest.data.repository.ModelRepository

class MainViewModelFactory(
    private val repository: ModelRepository
) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(
        modelClass: Class<T>
    ): T {

        if (modelClass.isAssignableFrom(MainViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return MainViewModel(repository) as T
        }

        throw IllegalArgumentException(
            "Unknown ViewModel class"
        )
    }
}