package me.deceptions.reservedslots.listeners

import org.bukkit.Bukkit
import org.bukkit.ChatColor
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.player.PlayerLoginEvent

import me.deceptions.reservedslots.Main

class Join(private val main: Main) : Listener {

    @EventHandler
    fun onJoin(e: PlayerLoginEvent) {
        val player = e.player
        val maxPlayers = main.config.getInt("MaxPlayers")
        if (main.configFile.getBoolean("PluginActive")) {
            if (Bukkit.getOnlinePlayers().size == maxPlayers) {
                if (!player.hasPermission("reservedslot.bypass")) {
                    player.kickPlayer(ChatColor.translateAlternateColorCodes('&', main.configFile.getString("FullServerKickMessage")))
                }
            }
        }
    }
}
