package com.infusory.machinetest.ui.viewer

import android.graphics.Paint
import androidx.compose.foundation.Canvas
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.drawIntoCanvas
import androidx.compose.ui.graphics.nativeCanvas
import androidx.compose.ui.platform.LocalContext
import com.infusory.machinetest.renderer.LabelProjector
import com.infusory.machinetest.utils.GlbJsonParser

@Composable
fun ModelLabelOverlay(
    assetPath: String,
    widthPx: Float,
    heightPx: Float,
    yaw: Float,
    pitch: Float,
    modelScale: Float,
    modifier: Modifier = Modifier
) {
    val context = LocalContext.current
    val labels = remember(assetPath) {
        GlbJsonParser.parseLabels(context, assetPath)
    }

    val textPaint = remember {
        Paint(Paint.ANTI_ALIAS_FLAG).apply {
            color = android.graphics.Color.WHITE
            textSize = 30f
        }
    }

    Canvas(modifier = modifier) {
        labels.forEach { label ->
            val anchor = LabelProjector.project(
                label = label,
                width = widthPx,
                height = heightPx,
                yawDegrees = yaw,
                pitchDegrees = pitch,
                modelScale = modelScale
            ) ?: return@forEach

            val labelPosition = Offset(
                x = (anchor.x + 34f).coerceIn(12f, size.width - 150f),
                y = (anchor.y - 28f).coerceIn(28f, size.height - 18f)
            )

            drawCircle(
                color = Color(0xFFFBBF24),
                radius = 5f,
                center = anchor
            )

            drawLine(
                color = Color.White,
                start = anchor,
                end = Offset(labelPosition.x - 8f, labelPosition.y + 5f),
                strokeWidth = 2f
            )

            drawIntoCanvas {
                it.nativeCanvas.drawText(
                    label.text,
                    labelPosition.x,
                    labelPosition.y,
                    textPaint
                )
            }
        }
    }
}
