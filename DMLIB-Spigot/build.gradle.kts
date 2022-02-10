import org.jetbrains.kotlin.gradle.tasks.KotlinCompile
import com.github.jengelman.gradle.plugins.shadow.tasks.ShadowJar

plugins {
    kotlin("jvm") version "1.5.10"
    id("com.github.johnrengelman.shadow") version "6.1.0"
}

group = "ru.draimdev"
version = "1.0.0"

repositories {
    mavenCentral()
    maven {
        name = "spigotmc-repo"
        url = uri("https://hub.spigotmc.org/nexus/content/repositories/snapshots/")
    }
    maven {
        name = "sonatype"
        url = uri("https://oss.sonatype.org/content/groups/public/")
    }
    maven {
        name = "jitpack.io"
        url = uri("https://jitpack.io")
    }
    maven {
        name = "placeholderapi"
        url = uri("https://repo.extendedclip.com/content/repositories/placeholderapi/")
    }
    maven {
        name = "sonatype-oss"
        url = uri("https://oss.sonatype.org/content/repositories/snapshots/")
    }
    maven {
        name = "codemc-repo"
        url = uri("https://repo.codemc.io/repository/maven-public/")
    }
}

dependencies {
    testImplementation(kotlin("test-junit"))

    // Kotlin
    implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8")

    // Quality Of Life
    implementation("xyz.xenondevs:particle:1.5.1")
    implementation("com.github.cryptomorin:XSeries:7.9.1.1")

    implementation("net.kyori:adventure-api:4.9.3")
    implementation("net.kyori:adventure-text-minimessage:4.1.0-SNAPSHOT")
    implementation("net.kyori:adventure-platform-bukkit:4.0.1")

    implementation("org.bstats:bstats-bukkit:2.2.1")
    implementation("com.zaxxer:HikariCP:4.0.1")

    compileOnly("org.spigotmc:spigot-api:1.18.1-R0.1-SNAPSHOT")
    compileOnly("com.github.MilkBowl:VaultAPI:1.7")
    compileOnly("me.clip:placeholderapi:2.10.6")
    compileOnly("com.gmail.filoghost.holographicdisplays:holographicdisplays-api:2.4.0")
}

tasks.test {
    useJUnit()
}

tasks.build {
    dependsOn("shadowJar")
}


tasks.named<ShadowJar>("shadowJar") {
    exclude("**/*.kotlin_metadata")

    archiveName = "$baseName-$version.$extension"

    relocate("com.cryptomorin.xseries", "ru.draimdev.dmlibspigot.xseries")
    relocate("xyz.xenondevs", "ru.draimdev.dmlibspigot.xenondevs")
    relocate("org.bstats", "ru.draimdev.dmlibspigot.bstats")
    relocate("net.kyori", "ru.draimdev.dmlibspigot.kyori")
}

tasks.named<Jar>("jar") {
    enabled = false
}

tasks.withType<KotlinCompile>() {
    kotlinOptions.jvmTarget = "16"
}
