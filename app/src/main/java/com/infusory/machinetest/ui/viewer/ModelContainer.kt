package com.infusory.machinetest.ui.viewer

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.gestures.awaitEachGesture
import androidx.compose.foundation.gestures.detectTransformGestures
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.onSizeChanged
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.IntSize
import androidx.compose.ui.unit.dp
import com.infusory.machinetest.data.model.ModelInstanceState
import com.infusory.machinetest.data.model.ModelMode
import kotlin.math.roundToInt

@Composable
fun ModelContainer(
    state: ModelInstanceState,
    initialIndex: Int,
    onRemove: () -> Unit,
    onToggleInteraction: () -> Unit,
    onToggleLabels: () -> Unit
) {
    var x by remember(state.instanceId) { mutableFloatStateOf(30f + (initialIndex * 28f)) }
    var y by remember(state.instanceId) { mutableFloatStateOf(80f + (initialIndex * 36f)) }
    var width by remember(state.instanceId) { mutableFloatStateOf(300f) }
    var height by remember(state.instanceId) { mutableFloatStateOf(330f) }

    var yaw by remember(state.instanceId) { mutableFloatStateOf(0f) }
    var pitch by remember(state.instanceId) { mutableFloatStateOf(0f) }
    var modelScale by remember(state.instanceId) { mutableFloatStateOf(1f) }

    var pixelSize by remember(state.instanceId) { mutableStateOf(IntSize.Zero) }

    val gestureModifier = if (state.mode == ModelMode.NORMAL) {
        Modifier.pointerInput(
            state.instanceId,
            state.mode
        ) {

            awaitEachGesture {
                var startWidth = width
                var startHeight = height
                var initialDistance: Float? = null

                while (true) {

                    val event = awaitPointerEvent()

                    val pressedPointers = event.changes.filter {
                        it.pressed
                    }

                    if (pressedPointers.isEmpty()) {
                        break
                    }

                    when (pressedPointers.size) {

                        1 -> {
                            initialDistance = null

                            val change = pressedPointers.first()

                            val delta =
                                change.position -
                                        change.previousPosition

                            x += delta.x
                            y += delta.y

                            change.consume()
                        }

                        2 -> {
                            // 2 fingers = resize container only

                            val first =
                                pressedPointers[0].position

                            val second =
                                pressedPointers[1].position

                            val currentDistance =
                                (first - second).getDistance()

                            if (initialDistance == null) {
                                initialDistance = currentDistance
                                startWidth = width
                                startHeight = height
                            }

                            val baseDistance = initialDistance ?: currentDistance

                            if (baseDistance > 0f) {

                                val scaleFactor =
                                    currentDistance / baseDistance

                                val newWidth =
                                    (startWidth * scaleFactor)
                                        .coerceIn(
                                            180f,
                                            620f
                                        )

                                val newHeight =
                                    (startHeight * scaleFactor)
                                        .coerceIn(
                                            210f,
                                            680f
                                        )

                                width = newWidth
                                height = newHeight
                            }

                            pressedPointers.forEach {
                                it.consume()
                            }
                        }

                        else -> {
                            initialDistance = null
                        }
                    }
                }
            }
        }
    } else {
        Modifier.pointerInput(
            state.instanceId,
            state.mode
        ) {

            awaitEachGesture {

                var previousDistance: Float? = null

                while (true) {

                    val event = awaitPointerEvent()

                    val pressedPointers = event.changes.filter {
                        it.pressed
                    }

                    if (pressedPointers.isEmpty()) {
                        break
                    }

                    when (pressedPointers.size) {

                        1 -> {

                            previousDistance = null

                            val change = pressedPointers.first()
                            val delta = change.position - change.previousPosition

                            yaw += delta.x * 0.35f

                            pitch = (
                                    pitch + delta.y * 0.28f
                                    ).coerceIn(
                                    -75f,
                                    75f
                                )

                            change.consume()
                        }

                        2 -> {

                            val first = pressedPointers[0].position
                            val second = pressedPointers[1].position

                            val currentDistance =
                                (first - second).getDistance()

                            previousDistance?.let { previous ->

                                if (previous > 0f) {

                                    val zoomFactor =
                                        currentDistance / previous

                                    modelScale = (
                                            modelScale * zoomFactor
                                            ).coerceIn(
                                            0.5f,
                                            3.5f
                                        )
                                }
                            }

                            previousDistance = currentDistance

                            pressedPointers.forEach {
                                it.consume()
                            }
                        }

                        else -> {
                            previousDistance = null
                        }
                    }
                }
            }
        }
    }

    Column(
        modifier = Modifier
            .offset { IntOffset(x.roundToInt(), y.roundToInt()) }
            .width(width.dp)
            .height(height.dp)
            .border(
                width = 1.dp,
                color = if (state.mode == ModelMode.INTERACTION) Color(0xFF22C55E) else Color(
                    0xFF64748B
                ),
                shape = RoundedCornerShape(14.dp)
            )
            .background(Color(0xEE1F2937), RoundedCornerShape(14.dp))
            .then(gestureModifier)
    ) {
        ModelControls(
            interactionEnabled = state.mode == ModelMode.INTERACTION,
            labelsVisible = state.labelsVisible,
            onToggleInteraction = onToggleInteraction,
            onToggleLabels = onToggleLabels,
            onRemove = onRemove
        )

        Box(
            modifier = Modifier
                .fillMaxSize()
                .onSizeChanged { pixelSize = it }
        ) {
            ModelScene(
                assetPath = state.model.assetPath,
                yaw = yaw,
                pitch = pitch,
                scale = modelScale,
                modifier = Modifier.fillMaxSize()
            )

            if (state.labelsVisible) {
                ModelLabelOverlay(
                    assetPath = state.model.assetPath,
                    widthPx = pixelSize.width.toFloat(),
                    heightPx = pixelSize.height.toFloat(),
                    yaw = yaw,
                    pitch = pitch,
                    modelScale = modelScale,
                    modifier = Modifier.fillMaxSize()
                )
            }
        }
    }
}
