package ru.draimdev.dmlibspigot.Sound

import com.cryptomorin.xseries.XSound
import org.bukkit.Sound

class SoundBuilder(var sound: Sound, var volume: Float, var pitch: Float) {

    constructor(xSound: XSound, volume: Float, pitch: Float) : this(xSound.parseSound()!!, volume, pitch)

    constructor(pljrSound: DMSound) : this(pljrSound.type, pljrSound.volume, pljrSound.pitch)

    constructor() : this(XSound.ENTITY_VILLAGER_NO, 1f, 1f)

    constructor(sound: Sound) : this(sound, 1f, 1f)

    constructor(xSound: XSound) : this(xSound.parseSound()!!)

    fun withSound(sound: Sound): SoundBuilder {
        this.sound = sound
        return this
    }

    fun withVolume(volume: Float): SoundBuilder {
        this.volume = volume
        return this
    }

    fun withPitch(pitch: Float): SoundBuilder {
        this.pitch = pitch
        return this
    }

    fun create(): DMSound {
        return DMSound(sound, volume, pitch)
    }
}