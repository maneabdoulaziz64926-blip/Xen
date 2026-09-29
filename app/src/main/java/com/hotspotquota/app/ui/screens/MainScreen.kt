package com.hotspotquota.app.ui.screens

import android.content.Intent
import android.provider.Settings
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.History
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.filled.WifiOff
import androidx.compose.material.icons.filled.WifiTethering
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.hotspotquota.app.backend.HotspotState
import com.hotspotquota.app.data.model.ClientDevice
import com.hotspotquota.app.ui.components.*
import com.hotspotquota.app.ui.viewmodel.HotspotViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScreen(viewModel: HotspotViewModel) {
    val context = LocalContext.current
    val devices by viewModel.devices.collectAsState()
    val hotspotState by viewModel.hotspotState.collectAsState()
    val isHotspotActive = hotspotState is HotspotState.Enabled
    val isMaxReached = devices.size >= HotspotViewModel.MAX_DEVICES

    Scaffold(
        topBar = { ... },
        floatingActionButton = {
            // Le bouton d'action n'apparaît QUE lorsque le point d'accès est activé
            if (isHotspotActive) {
                ExtendedFloatingActionButton(...)
            }
        }
    ) { paddingValues ->
        // 1. POINT D'ACCÈS INACTIF : RIEN DU MENU NE S'AFFICHE, ÉCRAN DE VEILLE
        if (!isHotspotActive) {
            Column(
                modifier = Modifier.fillMaxSize().padding(paddingValues),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                Icon(Icons.Default.WifiOff, modifier = Modifier.size(64.dp))
                Text("Point d'accès Wi-Fi Inactif", fontWeight = FontWeight.Bold)
                Text("L'application est en veille. Dès activation du point d'accès, tout le menu de contrôle apparaîtra automatiquement.")
                Button(onClick = { context.startActivity(Intent("android.settings.TETHER_SETTINGS")) }) {
                    Text("Activer le Point d'accès")
                }
            }
        } else {
            // 2. POINT D'ACCÈS ACTIVÉ : TOUT LE MENU DE CONTRÔLE APPARAÎT !
            LazyColumn {
                item { HotspotStatusCard(...) }
                item { BackendInfoBanner(...) }
                // Liste de tous les appareils connectés (Samsung Galaxy, etc.) avec leurs jauges et contrôles de quota
                items(devices) { device ->
                    DeviceCard(device = device, ...)
                }
            }
        }
    }
}