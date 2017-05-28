package me.deceptions.reservedslots.commands

import me.deceptions.reservedslots.Main
import org.apache.commons.lang.math.NumberUtils
import org.bukkit.ChatColor
import org.bukkit.command.Command
import org.bukkit.command.CommandExecutor
import org.bukkit.command.CommandSender
import org.bukkit.entity.Player

import java.io.IOException

class RSCmd(private val main: Main) : CommandExecutor {

    override fun onCommand(s: CommandSender, c: Command, label: String, args: Array<String>): Boolean {

        if (s !is Player) {
            s.sendMessage("You must be a player to do this command!")
            return true
        }

        val p = s

        if (c.name.equals("reservedslots", ignoreCase = true)) {
            if (args.isEmpty()) {
                p.sendMessage(col("&c&m------------- &3 ReservedSlots &c&m-------------"))
                p.sendMessage(col("&7ReservedSlots running version &a" + main.description.version + "&7 by Aderm"))
                p.sendMessage(col("&7Plugin commands - /rs help!"))
                p.sendMessage(col("&c&m------------------------------------"))

                return true

            } else if (args.size == 1) {
                if (args[0].equals("reload", ignoreCase = true) && p.hasPermission("reservedslots.admin")) {
                    main.reloadConfig()
                    p.sendMessage(col("&aConfig was reloaded sucesfully!"))
                    return true
                }

                if (args[0].equals("help", ignoreCase = true) && p.hasPermission("reservedslots.admin")) {
                    p.sendMessage(col("&c&m------------- &3 ReservedSlots Help Page &c&m-------------"))
                    p.sendMessage(col("&7/rs set kickmessage <kick message> - Message a player gets when server is full."))
                    p.sendMessage(col("&7/rs set reservedslots <reserved slots> - Sets reserved slots."))
                    p.sendMessage(col("&7/rs set maxplayers <max players> - Sets max players."))
                    p.sendMessage(col("&7/rs enable - Activates the plugin."))
                    p.sendMessage(col("&7/rs disable - Disables the plugin."))
                    p.sendMessage(col("&7/rs reload - Reloads config."))
                    p.sendMessage(col("&c&m-----------------------------------------------"))
                    return true
                }

                if (args[0].equals("enable", ignoreCase = true) && p.hasPermission("reservedslots.admin")) {
                    main.configFile.set("PluginActive", true)
                    p.sendMessage(col("&aPlugin enabled!"))
                    return true
                }

                if (args[0].equals("disable", ignoreCase = true) && p.hasPermission("reservedslots.admin")) {
                    main.configFile.set("PluginActive", false)
                    p.sendMessage(col("&cPlugin disabled!"))
                    return true
                }

            } else if (args.size >= 3) {
                if (args[1].equals("maxplayers", ignoreCase = true)) {

                    if (NumberUtils.isNumber(args[2])) {
                        main.configFile.set("MaxPlayers", Integer.parseInt(args[2]))
                        p.sendMessage(col("&3Max player slots set to &a" + args[2]))
                        try {
                            main.configFile.save(main.ConfigFile)
                        } catch (e: IOException) {
                            e.printStackTrace()
                        }

                        main.reloadConfig()
                        return true
                    } else {
                        p.sendMessage(col("&cThat is not a valid number!"))
                        return true
                    }

                }

                if (args[1].equals("reservedslots", ignoreCase = true)) {

                    if (NumberUtils.isNumber(args[2])) {
                        main.configFile.set("ReservedSlots", Integer.parseInt(args[2]))
                        p.sendMessage(col("&3Reserved slots set to &a" + args[2]))
                        try {
                            main.configFile.save(main.ConfigFile)
                        } catch (e: IOException) {
                            e.printStackTrace()
                        }

                        main.reloadConfig()
                        return true
                    } else {
                        p.sendMessage(col("&cThat is not a valid number!"))
                        return true
                    }

                }

                if (args[1].equals("kickmessage", ignoreCase = true)) {

                    val sb = StringBuilder()
                    for (i in 2..args.size - 1) {
                        sb.append(args[i]).append(" ")
                    }

                    val msg = sb.toString().trim { it <= ' ' }
                    val kickMessage = ChatColor.translateAlternateColorCodes('&', msg)
                    main.configFile.set("ServerFullKickMessage", kickMessage.toString())
                    p.sendMessage(col("&3Server full kick message set to &a$kickMessage&a"))
                    try {
                        main.configFile.save(main.ConfigFile)
                    } catch (e: IOException) {
                        e.printStackTrace()
                    }

                    main.reloadConfig()
                    return true
                }
            }
        }
        return false
    }

    private fun col(text: String): String {
        return ChatColor.translateAlternateColorCodes('&', text)
    }

}