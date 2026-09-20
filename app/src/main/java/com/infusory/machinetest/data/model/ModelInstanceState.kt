package com.infusory.machinetest.data.model

import java.util.UUID

data class ModelInstanceState(
    val instanceId: String = UUID.randomUUID().toString(),
    val model: Model3D,
    val mode: ModelMode = ModelMode.NORMAL,
    val labelsVisible: Boolean = false
)
