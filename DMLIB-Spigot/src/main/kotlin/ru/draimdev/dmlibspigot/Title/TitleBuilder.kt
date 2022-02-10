package ru.draimdev.dmlibspigot.Title

import net.kyori.adventure.text.Component
import net.kyori.adventure.text.TextReplacementConfig

class TitleBuilder(var title: Component, var subtitle: Component, var inTime: Long, var stayTime: Long, var outTime: Long) {

    constructor() : this(Component.text(""), Component.text(""), 20, 40, 20)

    constructor(title: Component) : this(title, Component.text(""), 20, 40, 20)

    @Deprecated("Use Adventure API's Component instead of String.",
        ReplaceWith("TitleBuilder(Component.text(title))", "net.kyori.adventure.text.Component"))
    constructor(title: String) : this(Component.text(title))

    constructor(title: Component, subtitle: Component) : this(title, subtitle, 20, 40, 20)

    @Deprecated("Use Adventure API's Component instead of String.",
        ReplaceWith("TitleBuilder(Component.text(title), Component.text(subtitle))", "net.kyori.adventure.text.Component"))
    constructor(title: String, subtitle: String) : this(Component.text(title), Component.text(subtitle))

    constructor(title: DMTitle) : this(title.title, title.subtitle, title.inTime, title.stayTime, title.outTime)

    fun withTitle(title: Component): TitleBuilder {
        this.title = title
        return this
    }

    @Deprecated("Use Adventure API's Component instead of String.",
        ReplaceWith("withTitle(Component.text(title))", "net.kyori.adventure.text.Component"))
    fun withTitle(title: String) = withTitle(Component.text(title))

    fun replaceTitle(target: String, replacement: String): TitleBuilder {
        title = title.replaceText(TextReplacementConfig.builder().matchLiteral(target).replacement(replacement).build())
        return this
    }

    fun withSubtitle(subtitle: Component): TitleBuilder {
        this.subtitle = subtitle
        return this
    }

    @Deprecated("Use Adventure API's Component instead of String.",
        ReplaceWith("withSubtitle(Component.text(subtitle))", "net.kyori.adventure.text.Component"))
    fun withSubtitle(subtitle: String) = withSubtitle(Component.text(subtitle))

    fun replaceSubtitle(target: String, replacement: String): TitleBuilder {
        subtitle = subtitle.replaceText(TextReplacementConfig.builder().matchLiteral(target).replacement(replacement).build())
        return this
    }

    fun withTimes(inTime: Long, stayTime: Long, outTime: Long): TitleBuilder {
        this.inTime = inTime
        this.stayTime = stayTime
        this.outTime = outTime
        return this
    }

    fun create(): DMTitle {
        return DMTitle(title, subtitle, inTime, stayTime, outTime)
    }
}