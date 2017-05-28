package me.deceptions.reservedslots.listeners

import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.server.ServerListPingEvent

import me.deceptions.reservedslots.Main

class PingEvent(private val main: Main) : Listener {

    @EventHandler
    fun onPingEvent(e: ServerListPingEvent) {
        val maxPlayers = main.configFile.getInt("MaxPlayers")
        val reservedSlots = main.configFile.getInt("ReservedSlots")
        if (main.configFile.getBoolean("PluginActive")) {
            e.maxPlayers = maxPlayers - reservedSlots
        }
    }

}
