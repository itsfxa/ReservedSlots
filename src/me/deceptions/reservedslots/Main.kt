package me.deceptions.reservedslots

import me.deceptions.reservedslots.commands.RSCmd
import java.io.File
import java.io.IOException

import org.bukkit.Bukkit
import org.bukkit.ChatColor
import org.bukkit.configuration.file.FileConfiguration
import org.bukkit.configuration.file.YamlConfiguration
import org.bukkit.plugin.java.JavaPlugin

import me.deceptions.reservedslots.listeners.Join
import me.deceptions.reservedslots.listeners.PingEvent

class Main : JavaPlugin() {

    var ConfigFile = File(dataFolder, "config.yml")
    var configFile: FileConfiguration = YamlConfiguration.loadConfiguration(ConfigFile)

    override fun onEnable() {
        logger("&c----- &3Reserved Slots &c-----")
        logger("ReservedSlots running version: " + description.version)
        logger("ReservedSlots author: " + description.authors)
        logger("&c----- &3Reserved Slots &c-----")
        this.registerCommands()
        this.registerEvents()
        dataFolder.mkdirs()
        configFile.options().copyDefaults(true)
        try {
            configFile.save(ConfigFile)
        } catch (e: IOException) {
            e.printStackTrace()
        }

    }

    private fun registerEvents() {
        val pm = Bukkit.getPluginManager()
        pm.registerEvents(PingEvent(this), this)
        pm.registerEvents(Join(this), this)
    }

    fun createConfig() {
        configFile.createSection("MaxPlayers")
        configFile.set("MaxPlayers", 150)
        configFile.createSection("ReservedSlots")
        configFile.set("ReservedSlots", 25)
        configFile.createSection("ServerFullKickMessage")
        configFile.set("ServerFullKickMessage", "&cThe server is full, become a donor to bypass this!")
        configFile.addDefault("PluginActive", true)
    }

    private fun registerCommands() {
        getCommand("reservedslots").executor = RSCmd(this)
    }

    fun logger(text: String) {
        val string = ChatColor.translateAlternateColorCodes('&', text)
        Bukkit.getConsoleSender().sendMessage(string)
    }

}
