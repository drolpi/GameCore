plugins {
    id("maven-publish")
    id("com.github.johnrengelman.shadow") version "7.1.2"
}

defaultTasks("build", "shadowJar")

allprojects {
    group = "de.drolpi"
    version = "1.0.0-SNAPSHOT"

    repositories {
        mavenCentral()
        maven("https://hub.spigotmc.org/nexus/content/repositories/snapshots/")
        maven("https://repo.papermc.io/repository/maven-public/")
        maven("https://nexus.velocitypowered.com/repository/maven-public/")
    }
}

subprojects {
    apply(plugin = "java-library")
    apply(plugin = "maven-publish")
    apply(plugin = "com.github.johnrengelman.shadow")

    dependencies {
        "implementation"("org.jetbrains:annotations:23.0.0")
        "implementation"("com.google.code.gson:gson:2.10.1")

        "compileOnly"("io.papermc.paper:paper-api:1.20.4-R0.1-SNAPSHOT")
    }
}

tasks.withType<JavaCompile> {
    sourceCompatibility = JavaVersion.VERSION_17.toString()
    targetCompatibility = JavaVersion.VERSION_17.toString()
    options.encoding = "UTF-8"
    options.isIncremental = true

}
