import net.minecraftforge.gradle.common.util.MinecraftExtension

buildscript {
    repositories {
        mavenCentral()
        jcenter()
        maven("https://files.minecraftforge.net/maven")
    }
    dependencies {
        classpath("net.minecraftforge.gradle:ForgeGradle:3.+")
    }
}

plugins {
    kotlin("jvm") version "1.4.21"
}

apply {
    plugin("net.minecraftforge.gradle")
}

version = "1.0"
group = "me.mocha.forgemod"

repositories {
    jcenter()
    mavenCentral()
    maven("http://maven.shadowfacts.net/")
    maven("https://thedarkcolour.github.io/KotlinForForge/")
}

dependencies {
    implementation(kotlin("stdlib"))
    implementation("thedarkcolour:kotlinforforge:1.7.0")
    "minecraft"("net.minecraftforge:forge:1.16.4-35.1.28")
}

configure<MinecraftExtension> {
    mappings("snapshot", "20201028-1.16.3")

    runs {
        create("client")
        create("server")
        create("data") {
            args("--mod", "coin", "--all", "--output", file("src/generated/resources/"), "--existing", file("src/main/resources/"))
        }

        configureEach {
            workingDirectory(project.file("run/$name"))
            property("forge.logging.markers", "SCAN,REGISTRIES,REGISTRYDUMP")
            property("forge.logging.console.level", "debug")

            mods {
                create("coin") {
                    source(sourceSets["main"])
                }
            }
        }
    }
}

sourceSets {
    main {
        resources {
            srcDir("src/generated/resources")
        }
    }
}