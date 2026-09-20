package com.infusory.machinetest.utils

import android.content.Context
import com.infusory.machinetest.data.model.ModelLabel
import org.json.JSONObject
import java.nio.ByteBuffer
import java.nio.ByteOrder

object GlbJsonParser {

    private const val GLB_MAGIC = 0x46546C67
    private const val JSON_CHUNK_TYPE = 0x4E4F534A

    fun parseLabels(context: Context, assetPath: String): List<ModelLabel> {
        return runCatching {
            val bytes = context.assets.open(assetPath).use { it.readBytes() }
            parseLabels(bytes)
        }.getOrElse { emptyList() }
    }

    fun parseLabels(bytes: ByteArray): List<ModelLabel> {
        if (bytes.size < 20) return emptyList()

        val buffer = ByteBuffer.wrap(bytes).order(ByteOrder.LITTLE_ENDIAN)
        val magic = buffer.int
        if (magic != GLB_MAGIC) return emptyList()

        buffer.int // version
        buffer.int // total length

        while (buffer.remaining() >= 8) {
            val chunkLength = buffer.int
            val chunkType = buffer.int
            if (chunkLength < 0 || chunkLength > buffer.remaining()) break

            val chunk = ByteArray(chunkLength)
            buffer.get(chunk)

            if (chunkType == JSON_CHUNK_TYPE) {
                val json = chunk.toString(Charsets.UTF_8).trimEnd('\u0000', ' ', '\n', '\r', '\t')
                return parseJson(json)
            }
        }
        return emptyList()
    }

    private fun parseJson(json: String): List<ModelLabel> {
        val root = JSONObject(json)
        val nodes = root.optJSONArray("nodes") ?: return emptyList()
        val out = ArrayList<ModelLabel>()

        for (i in 0 until nodes.length()) {
            val node = nodes.optJSONObject(i) ?: continue
            val extras = node.optJSONObject("extras") ?: continue
            val prop = extras.optString("prop").takeIf { it.isNotBlank() } ?: continue
            val name = node.optString("name", "Node_$i")

            val translation = node.optJSONArray("translation")
            val x = translation?.optDouble(0, 0.0)?.toFloat() ?: 0f
            val y = translation?.optDouble(1, 0.0)?.toFloat() ?: 0f
            val z = translation?.optDouble(2, 0.0)?.toFloat() ?: 0f

            out += ModelLabel(
                nodeName = name,
                text = prop,
                localX = x,
                localY = y,
                localZ = z
            )
        }
        return out
    }
}
