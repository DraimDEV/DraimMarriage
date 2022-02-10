package ru.draimdev.dmlibspigot.Commands

import org.bukkit.command.CommandSender
import org.bukkit.command.ConsoleCommandSender
import org.bukkit.entity.Player
import ru.draimdev.dmlibspigot.Config.configuration.Lang
import ru.draimdev.dmlibspigot.Util.sendMessage
import java.util.*
import kotlin.collections.ArrayList

abstract class SubCommands(val command: String, val permission: String) {
    val subCommands: MutableList<SubCommands> = ArrayList()
    val arguments: MutableList<String> = ArrayList()

    fun onTabComplete(args: List<String>) : List<String> {
        if (args.isEmpty()) {
            return if (arguments.isEmpty()) {
                val list: MutableList<String> = ArrayList()
                subCommands.forEach { list.add(it.command) }
                list
            } else {
                arguments
            }
        } else {
            subCommands.forEach {
                if (it.equals(args[0])) {
                    return it.onTabComplete(args.drop(1))
                }
            }
        }
        return emptyList()
    }

    fun onCommand(sender: CommandSender, args: Array<String>) : Boolean {
        if (checkPerm(sender, permission)){
            if (args.isNotEmpty()) {
                subCommands.forEach {
                    if (it.command.equals(args[0], ignoreCase = true)) {
                        return it.onCommand(sender, args.copyOfRange(1, args.size))
                    }
                }
            }
            return when (sender) {
                is Player -> {
                    onPlayerCommand(sender, args)
                    true
                }
                is ConsoleCommandSender -> {
                    onConsoleCommand(sender, args)
                    true
                }
                else -> {
                    sender.sendMessage("Unknown Sender")
                    false
                }
            }
        }
        return false
    }

    open fun onPlayerCommand(player: Player, args: Array<String>) = sendMessage(player, Lang.COMMAND_RESPONSE_PLAYER.get())

    open fun onConsoleCommand(sender: ConsoleCommandSender, args: Array<String>) = sendMessage(sender, Lang.COMMAND_RESPONSE_CONSOLE.get())

    fun checkPerm(sender: CommandSender, perm: String): Boolean {
        if (sender !is Player) return true
        if (sender.hasPermission(perm)) return true
        sendMessage(sender, Lang.NO_PERM.get())
        return false
    }
}