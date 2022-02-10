package ru.draimdev.dmlibspigot.Util

fun isInt(number: String): Boolean {
    return try {
        number.toInt()
        true
    } catch (e: NumberFormatException) {
        false
    }
}