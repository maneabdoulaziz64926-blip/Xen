package com.hotspotquota.app.backend

import android.content.Context
import android.util.Log
import com.hotspotquota.app.data.model.ClientDevice
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class PrivilegedBackend(private val context: Context) : TrafficControlBackend {

    override suspend fun blockClientTraffic(device: ClientDevice): TrafficBlockResult = withContext(Dispatchers.IO) {
        val ip = device.ipAddress
        // Expulsion et isolation stricte du client ayant dépassé son quota
        val commands = mutableListOf(
            "iptables -D FORWARD -s $ip -j DROP 2>/dev/null",
            "iptables -D FORWARD -d $ip -j DROP 2>/dev/null",
            "iptables -D INPUT -s $ip -j DROP 2>/dev/null",
            "iptables -I FORWARD 1 -s $ip -j DROP",
            "iptables -I FORWARD 1 -d $ip -j DROP",
            "iptables -I INPUT 1 -s $ip -j DROP"
        )
        if (!device.macAddress.isNullOrBlank() && device.macAddress != "02:00:00:00:00:00") {
            commands.add("hostapd_cli deauthenticate ${device.macAddress} 2>/dev/null")
        }
        executeRootCommands(commands)
        TrafficBlockResult.Success("Client $ip expulsé du hotspot et interdit de reconnexion tant que le quota n'est pas réinitialisé.")
    }

    override suspend fun unblockClientTraffic(device: ClientDevice): TrafficBlockResult = withContext(Dispatchers.IO) {
        val ip = device.ipAddress
        val commands = listOf(
            "iptables -D FORWARD -s $ip -j DROP 2>/dev/null",
            "iptables -D FORWARD -d $ip -j DROP 2>/dev/null",
            "iptables -D INPUT -s $ip -j DROP 2>/dev/null"
        )
        executeRootCommands(commands)
        TrafficBlockResult.Success("Trafic Internet restauré pour l'IP $ip.")
    }
}