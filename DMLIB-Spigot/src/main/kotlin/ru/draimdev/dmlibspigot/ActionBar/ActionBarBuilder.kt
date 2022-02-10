package ru.draimdev.dmlibspigot.ActionBar

import ru.draimdev.dmlibspigot.Util.colorString

class ActionBarBuilder(var message: String, var duration: Long) {

    constructor() : this("", 20)

    constructor(message: String) : this(message, 20)

    fun withMessage(message: String): ActionBarBuilder {
        this.message = message
        return this
    }

    fun replaceMessage(target: String, replacement: String): ActionBarBuilder {
        message = message.replace(target, replacement)
        return this
    }

    fun withDuration(duration: Long): ActionBarBuilder {
        this.duration = duration
        return this
    }

    fun create(): DMActionBar {
        return DMActionBar(colorString(message), duration)
    }
}