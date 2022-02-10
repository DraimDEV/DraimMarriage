package ru.draimdev.dmlibspigot.Commands

import org.bukkit.command.Command
import org.bukkit.command.CommandExecutor
import org.bukkit.command.CommandSender
import org.bukkit.command.TabCompleter
import org.bukkit.plugin.java.JavaPlugin

abstract class MainCommands(command: String, permission: String) : CommandExecutor, TabCompleter, SubCommands(command, permission) {
    constructor(command: String) : this(command, "")

    fun registerCommand(plugin: JavaPlugin) {
        if (plugin.getCommand(command) != null) {
            plugin.getCommand(command)!!.tabCompleter = this
            plugin.getCommand(command)!!.setExecutor(this)
        }
    }

    final override fun onTabComplete(
        sender: CommandSender,
        command: Command,
        alias: String,
        args: Array<out String>
    ) = onTabComplete(args.asList()).toMutableList()

    final override fun onCommand(
        sender: CommandSender,
        command: Command,
        label: String,
        args: Array<String>
    ) = onCommand(sender, args)
}