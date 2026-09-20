package com.infusory.machinetest.renderer

import androidx.compose.ui.geometry.Offset
import com.infusory.machinetest.data.model.ModelLabel
import kotlin.math.cos
import kotlin.math.sin
object LabelProjector {

    fun project(
        label: ModelLabel,
        width: Float,
        height: Float,
        yawDegrees: Float,
        pitchDegrees: Float,
        modelScale: Float
    ): Offset? {
        if (width <= 0f || height <= 0f) return null

        val yaw = Math.toRadians(yawDegrees.toDouble())
        val pitch = Math.toRadians(pitchDegrees.toDouble())

        val x0 = label.localX.toDouble()
        val y0 = label.localY.toDouble()
        val z0 = label.localZ.toDouble()

        val x1 = x0 * cos(yaw) + z0 * sin(yaw)
        val z1 = -x0 * sin(yaw) + z0 * cos(yaw)

        val y2 = y0 * cos(pitch) - z1 * sin(pitch)
        val z2 = y0 * sin(pitch) + z1 * cos(pitch)

        val cameraDistance = 3.2
        val depth = cameraDistance - z2
        if (depth <= 0.15) return null

        val perspective = (1.35 / depth) * modelScale
        val sx = width * 0.5f + (x1 * width * perspective * 0.5).toFloat()
        val sy = height * 0.5f - (y2 * height * perspective * 0.5).toFloat()

        if (sx < -80f || sx > width + 80f || sy < -80f || sy > height + 80f) return null
        return Offset(sx, sy)
    }
}
