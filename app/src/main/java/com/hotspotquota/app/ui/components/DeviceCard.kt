package com.hotspotquota.app.ui.components

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import com.hotspotquota.app.data.model.ClientDevice

@Composable
fun DeviceCard(
    device: ClientDevice,
    canBlockTraffic: Boolean,
    onEditClick: (ClientDevice) -> Unit,
    onToggleQuotaClick: (ClientDevice) -> Unit,
    onResetConsumptionClick: (ClientDevice) -> Unit,
    onExpelClick: ((ClientDevice) -> Unit)? = null,
    onUnblockClick: ((ClientDevice) -> Unit)? = null,
    onDeleteClick: ((ClientDevice) -> Unit)? = null
) {
    Card(...) {
        // En-tête : Modèle (Samsung Galaxy, etc.) et statut
        // Jauge de consommation et quota
        // Bouton Éjecter : permet d'éjecter immédiatement l'appareil même sans quota atteint
        if (device.isExpelled) {
            Button(onClick = { onUnblockClick?.invoke(device) }) {
                Text("Réautoriser")
            }
        } else {
            OutlinedButton(onClick = { onExpelClick?.invoke(device) }) {
                Text("Éjecter")
            }
        }
    }
}