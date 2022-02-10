package ru.draimdev.dmlibspigot.ScoreBoard

import ru.draimdev.dmlibspigot.Util.colorString


class ScoreBoardBuilder(var title: String, var lines: MutableList<String>) {

    constructor() : this("", ArrayList())

    constructor(title: String) : this(title, ArrayList())

    constructor(title: String, vararg lines: String) : this(title, lines.toMutableList())

    constructor(scoreboard: DMScoreBoard) : this(scoreboard.title, scoreboard.lines)

    fun withTitle(title: String): ScoreBoardBuilder {
        this.title = title
        return this
    }

    fun withLines(lines: MutableList<String>): ScoreBoardBuilder {
        this.lines = lines
        return this
    }

    fun withLines(vararg lines: String): ScoreBoardBuilder {
        this.lines = lines.toMutableList()
        return this
    }

    fun replaceLines(target: String, input: String): ScoreBoardBuilder {
        val replacedLines: MutableList<String> = ArrayList()
        lines.forEach { replacedLines.add(it.replace(target, input)) }
        lines = replacedLines
        return this
    }

    fun create(): DMScoreBoard {
        val coloredLines: MutableList<String> = java.util.ArrayList()
        lines.forEach { coloredLines.add(colorString(it)) }
        return DMScoreBoard(colorString(title), coloredLines)
    }
}