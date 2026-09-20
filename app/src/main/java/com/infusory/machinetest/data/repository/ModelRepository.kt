package com.infusory.machinetest.data.repository

import com.infusory.machinetest.data.model.Model3D


class ModelRepository {

    fun getAvailableModels(): List<Model3D> = listOf(
        Model3D("model_1", "Bulb", "models/Bulb.glb"),
        Model3D("model_2", "Fiagena", "models/Fiagena.glb"),
        Model3D("model_3", "Lungs", "models/Lungs.glb"),
        Model3D("model_4", "Microscope", "models/Microscope.glb"),
        Model3D("model_5", "Solarsystem", "models/solarsystem.glb")
    )
}
