package ru.draimdev.dmlibspigot.Util

import org.bukkit.OfflinePlayer
import ru.draimdev.dmlibspigot.Config.configuration.Settings
import ru.draimdev.dmlibspigot.DMLIBSpigot

private val ECONOMY = DMLIBSpigot.vaultEcon!!

private fun checkEnabled() : Boolean {
    if (Settings.VAULT) {
        return true
    } else if (Settings.DISABLED_MESSAGES) {
        DMLIBSpigot.instance.logger.warning("Попытка подхвата Vault")
    }
    return false
}

fun getBalance(target: OfflinePlayer): Double {
    return if (checkEnabled()) {
        ECONOMY.getBalance(target)
    } else {
        -1.0
    }
}

@Deprecated("Используйте OfflinePlayer вместо ника.")
fun getBalance(target: String): Double {
    return if (checkEnabled()) {
        ECONOMY.getBalance(target)
    } else {
        -1.0
    }
}

fun hasBalance(target: OfflinePlayer, amount: Double): Boolean {
    return if (checkEnabled()) {
        getBalance(target) >= amount
    } else {
        true
    }
}

@Deprecated("Используйте OfflinePlayer вместо ника.")
fun hasBalance(target: String, amount: Double): Boolean {
    return if (checkEnabled()) {
        getBalance(target) >= amount
    } else {
        true
    }
}

fun deposit(target: OfflinePlayer, amount: Double) {
    if (checkEnabled()) {
        ECONOMY.depositPlayer(target, amount)
    }
}

@Deprecated("Используйте OfflinePlayer вместо ника.")
fun deposit(target: String, amount: Double) {
    if (checkEnabled()) {
        ECONOMY.depositPlayer(target, amount)
    }
}

fun withdraw(target: OfflinePlayer, amount: Double) {
    if (checkEnabled()) {
        ECONOMY.withdrawPlayer(target, amount)
    }
}

@Deprecated("Используйте OfflinePlayer вместо ника.")
fun withdraw(target: String, amount: Double) {
    if (checkEnabled()) {
        ECONOMY.withdrawPlayer(target, amount)
    }
}