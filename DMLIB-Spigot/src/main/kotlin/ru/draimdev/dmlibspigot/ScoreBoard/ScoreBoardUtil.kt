package ru.draimdev.dmlibspigot.ScoreBoard

import org.bukkit.Bukkit
import org.bukkit.entity.Player
import ru.draimdev.dmlibspigot.Util.setPlaceholders

fun sendScoreBoard(player: Player, scoreboard: DMScoreBoard) {
    val builder = ScoreBoardBuilder(scoreboard)
    val linesWithPAPI: MutableList<String> = ArrayList()
    builder.lines.forEach { linesWithPAPI.add(setPlaceholders(player, it)) }
    builder.withLines(linesWithPAPI)
    player.scoreboard = builder.create().toScoreboard()
}

fun broadcastScoreBoard(scoreboard: DMScoreBoard) {
    Bukkit.getOnlinePlayers().forEach { sendScoreBoard(it, scoreboard) }
}