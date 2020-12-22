plugins {
    kotlin("jvm") version "1.4.21"
    id("com.github.johnrengelman.shadow") version "5.2.0"
    `maven-publish`
}

group = "me.mocha"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
    maven("https://papermc.io/repo/repository/maven-public/")
}

dependencies {
    implementation(kotlin("stdlib"))
    compileOnly("com.destroystokyo.paper", "paper-api", "1.16.4-R0.1-SNAPSHOT")
}

publishing {
    publications {
        create<MavenPublication>("EconomySystem") {
            artifactId = project.name
            from(components["java"])
            artifact(tasks.shadowJar)
        }
    }
}
