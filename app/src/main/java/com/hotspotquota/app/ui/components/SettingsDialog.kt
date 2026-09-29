package com.hotspotquota.app.ui.components

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable

@Composable
fun SettingsDialog(
    isDarkMode: Boolean,
    currentLanguage: String,
    onDarkModeToggle: (Boolean) -> Unit,
    onLanguageChange: (String) -> Unit,
    onDismiss: () -> Unit
) {
    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text(if (currentLanguage == "en") "Settings" else if (currentLanguage == "es") "Ajustes" else "Paramètres") },
        text = {
            Column {
                Row {
                    Text(if (isDarkMode) "Mode Sombre" else "Mode Clair")
                    Switch(checked = isDarkMode, onCheckedChange = onDarkModeToggle)
                }
                // Sélecteur de langue: FR, EN, ES
            }
        },
        confirmButton = { Button(onClick = onDismiss) { Text("OK") } }
    )
}