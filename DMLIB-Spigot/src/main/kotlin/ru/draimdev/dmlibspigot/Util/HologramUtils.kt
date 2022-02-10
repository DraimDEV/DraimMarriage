package ru.draimdev.dmlibspigot.Util

import com.gmail.filoghost.holographicdisplays.api.Hologram
import com.gmail.filoghost.holographicdisplays.api.HologramsAPI
import org.bukkit.Bukkit
import org.bukkit.ChatColor
import org.bukkit.Location
import ru.draimdev.dmlibspigot.Config.configuration.Settings
import ru.draimdev.dmlibspigot.DMLIBSpigot

fun createTemporaryHologram(location: Location, text: List<String>, ticks: Int) {
    if (!Settings.HOLOGRAMS) {
        Bukkit.getConsoleSender()
            .sendMessage(ChatColor.RED.toString() + "DMLIB: Пытался создать Hologram, отображаемую при отключении в конфигурации!")
        return
    }
    val hologram = HologramsAPI.createHologram(DMLIBSpigot.instance, location)
    for (line in text) {
        hologram!!.appendTextLine(line)
    }
    Bukkit.getScheduler().runTaskLaterAsynchronously(DMLIBSpigot.instance,
        Runnable {
            if (hologram != null && !hologram.isDeleted) {
                hologram.delete()
            }
        }, ticks.toLong()
    )
}