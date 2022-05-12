plugins {
    id("java")
    id("maven-publish")
    id("com.github.johnrengelman.shadow") version "7.1.2"
}

defaultTasks("build", "shadowJar")

allprojects {
    group = "de.natrox"
    version = "1.0.0-SNAPSHOT"

    repositories {
        mavenCentral()
        maven("https://hub.spigotmc.org/nexus/content/groups/public/")
        maven("https://oss.sonatype.org/content/groups/public/")
        maven("https://nexus.velocitypowered.com/repository/maven-public/")
    }
}

subprojects {
    apply(plugin = "java")
    apply(plugin = "maven-publish")
    apply(plugin = "com.github.johnrengelman.shadow")

    dependencies {
        implementation("org.jetbrains:annotations:23.0.0")
        implementation("com.google.inject:guice:4.0")

        compileOnly("org.spigotmc:spigot-api:1.8.8-R0.1-SNAPSHOT")
        testCompileOnly("org.spigotmc:spigot-api:1.8.8-R0.1-SNAPSHOT")
    }

    tasks.withType<JavaCompile> {
        options.encoding = "UTF-8"
        options.isIncremental = true
    }
}