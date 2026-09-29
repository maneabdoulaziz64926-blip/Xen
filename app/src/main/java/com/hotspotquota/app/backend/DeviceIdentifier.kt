package com.hotspotquota.app.backend

import java.util.Locale

object DeviceIdentifier {

    data class RecognizedInfo(
        val displayName: String,
        val vendor: String,
        val model: String?
    )

    fun identifyDevice(hostname: String?, mac: String?, fallbackIp: String): RecognizedInfo {
        val cleanHost = hostname?.trim().orEmpty()
        val lowerHost = cleanHost.lowercase(Locale.ROOT)

        if (cleanHost.isNotBlank() && cleanHost != fallbackIp) {
            when {
                lowerHost.contains("galaxy") || lowerHost.contains("samsung") || lowerHost.contains("sm-") -> {
                    val clean = cleanHost.replace("_", " ").replace("-", " ")
                    val model = if (!clean.lowercase().contains("samsung")) "Samsung $clean" else clean
                    return RecognizedInfo(displayName = model, vendor = "Samsung", model = model)
                }
                lowerHost.contains("iphone") -> {
                    return RecognizedInfo(displayName = cleanHost.replace("-", " "), vendor = "Apple", model = "iPhone")
                }
                lowerHost.contains("ipad") -> {
                    return RecognizedInfo(displayName = cleanHost.replace("-", " "), vendor = "Apple", model = "iPad")
                }
                lowerHost.contains("pixel") -> {
                    return RecognizedInfo(displayName = cleanHost.replace("-", " "), vendor = "Google", model = "Pixel")
                }
                lowerHost.contains("redmi") || lowerHost.contains("xiaomi") -> {
                    return RecognizedInfo(displayName = cleanHost.replace("-", " "), vendor = "Xiaomi", model = cleanHost)
                }
            }
        }
        val fallbackName = if (cleanHost.isNotBlank() && cleanHost != fallbackIp) cleanHost else "Appareil ($fallbackIp)"
        return RecognizedInfo(displayName = fallbackName, vendor = "Générique", model = null)
    }
}