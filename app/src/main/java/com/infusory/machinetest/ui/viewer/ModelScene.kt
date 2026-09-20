package com.infusory.machinetest.ui.viewer

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import io.github.sceneview.SceneView
import io.github.sceneview.math.Rotation
import io.github.sceneview.math.Scale
import io.github.sceneview.node.ModelNode
import io.github.sceneview.rememberEngine
import io.github.sceneview.rememberModelInstance
import io.github.sceneview.rememberModelLoader

@Composable
fun ModelScene(
    assetPath: String,
    yaw: Float,
    pitch: Float,
    scale: Float,
    modifier: Modifier = Modifier
) {

    val engine = rememberEngine()
    val modelLoader = rememberModelLoader(engine)

    val modelInstance = rememberModelInstance(
        modelLoader = modelLoader,
        assetFileLocation = assetPath
    )

    SceneView(
        modifier = modifier,
        engine = engine,
        modelLoader = modelLoader,
        cameraManipulator = null,
        autoCenterContent = true
    ) {

        modelInstance?.let { instance ->

            Node(
                scale = Scale(scale),
                rotation = Rotation(
                    x = pitch,
                    y = yaw,
                    z = 0f
                )
            ) {

                ModelNode(
                    modelInstance = instance,
                    autoAnimate = false,
                    scaleToUnits = 1.0f
                )
            }
        }
    }
}
